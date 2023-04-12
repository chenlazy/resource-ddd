package com.idanchuang.resource.server.infrastructure.common.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-29 16:57
 * @Desc: RESOURCE数据库配置
 * @Copyright VTN Limited. All rights reserved.
 */
@Configuration
@MapperScan(basePackages = {
        "com.idanchuang.cms.server.infrastructure.persistence.mapper",
        "com.idanchuang.resource.server.infrastructure.persistence.mapper",
        "com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper"},
        sqlSessionFactoryRef="sqlSessionFactoryBean")
public class DatasourceConfig {


    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String password;

    //  配置mapper的扫描，找到所有的mapper.xml映射文件
    @Value("classpath*:/mapper/*.xml")
    private String mapperLocations;

    @Value("classpath*:/mapper/new/*.xml")
    private String newMapperLocations;

    @Bean
    public HikariDataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(dbUsername);
        config.setPassword(password);
        //是否自定义配置，为true时下面两个参数才生效
        config.addDataSourceProperty("cachePrepStmts", "true");
        //连接池大小默认25，官方推荐250-500
        config.addDataSourceProperty("prepStmtCacheSize", "25");
        //单条语句最大长度默认256，官方推荐2048
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        //新版本MySQL支持服务器端准备，开启能够得到显著性能提升
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("useLocalTransactionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        config.addDataSourceProperty("maximum-pool-size", "15");
        config.addDataSourceProperty("minimum-idle", "15");
        return new HikariDataSource(config);
    }


    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(List<Interceptor> interceptors, @Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean dealerSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        dealerSqlSessionFactoryBean.setDataSource(dataSource);
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
        Resource[] newResources = new PathMatchingResourcePatternResolver().getResources(newMapperLocations);
        Resource[] allResources = (Resource[])ArrayUtils.addAll(resources, newResources);

        dealerSqlSessionFactoryBean.setMapperLocations(allResources);
        dealerSqlSessionFactoryBean.setPlugins(interceptors.toArray(new Interceptor[0]));
        return dealerSqlSessionFactoryBean.getObject();
    }

    /**
     * 多数据源默认事务，若想使用老数据源的事务，请在@Transactional注解指定事务源
     * @param dataSource
     * @return
     * @throws SQLException
     */
    @Primary
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) throws SQLException {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
