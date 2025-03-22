package shardingJDBC.classBased.algorithm;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.*;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-22
 * @Description: CLASS_BASED算法（分表算法）COMPLEX类型实现类
 * @Version: 1.0
 */
@Slf4j
// 泛型实现了Comparable<?>接口即可，官方不建议直接声明一个明确的泛型，而是在doSharding中进行类型转换，防止因为类型与声明类型不符合导致的错误
// 比如说我这里指定了Long类型，但是实际上我sql语句对c_status进行范围查询，c_status是int类型，这会导致在使用Range的.upperEndpoint()等方法时报错类型转换错误（Integer不能转为Long）
public class MyComplexAlgorithm implements ComplexKeysShardingAlgorithm {
    public Properties props;

    /**
     * @param collection               因为这个是配置的分表策略，这里的collection就是获取到的所有表名字信息（course_1、course_2）
     * @param complexKeysShardingValue 保持了两个map，一个可以获取到的sql语句中用到的分片键和对应的值（因为可能多个（in），使用LinkedList保存的），一个可以获取到sql语句中范围查询的键和其range信息
     */
    @Override
    public Collection<String> doSharding(Collection collection, ComplexKeysShardingValue complexKeysShardingValue) {
        Map<String, Collection> shardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        shardingValuesMap.forEach((k, v) -> {
            log.info("[ColumnNameAndShardingValuesMap] key: {}, value: {}", k, v);
        });
        Map<String, Range> rangeValuesMap = complexKeysShardingValue.getColumnNameAndRangeValuesMap();
        rangeValuesMap.forEach((k, v) -> {
            log.info("[ColumnNameAndRangeValuesMap] key: {}, value: {}", k, v);
        });
        // 可以根据range中的内容去不同的分表中查询，或者说判断range的合法性，不合法直接抛出异常阻断查询减少数据库压力
        // 在这里再进行类型转换，而不是直接指定接口的泛型！！否则可能报错类型转换失败！！
        Range<Integer> cStatusRange = rangeValuesMap.get("c_status");
        Integer cUpper = cStatusRange.upperEndpoint();
        Integer cLower = cStatusRange.lowerEndpoint();
        if (cUpper < cLower || cUpper < 0 || cLower > 5) {
            throw new RuntimeException("c_status数值不合法，拒绝该sql查询");
        }
        // 接着根据分片键执行分表逻辑，如按键%2+1分表
        List<String> targetTables = new ArrayList<>();
        // 获取逻辑表，用于拼接真实表的名字
        String logicTableName = complexKeysShardingValue.getLogicTableName();
        // 获取时才做类型转换
        Collection<Long> cidValues = shardingValuesMap.get("cid");
        cidValues.forEach(cid -> {
            String tableName = logicTableName + "_" + ((cid % 2) + 1);
            if (collection.contains(tableName)) {
                // 如果存在该表，则加入到结果中，重复也没问题
                targetTables.add(tableName);
            }
        });
        log.info("[targetTables]: {}", targetTables);
        return targetTables;
    }

    @Override
    public Properties getProps() {
        return this.props;
    }

    @Override
    public void init(Properties properties) {
        this.props = properties;
    }
}
