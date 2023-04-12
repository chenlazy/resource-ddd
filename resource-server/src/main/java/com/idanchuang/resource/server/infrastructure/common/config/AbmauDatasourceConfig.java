package com.idanchuang.resource.server.infrastructure.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-29 16:58
 * @Desc: ABMAU 数据库 config
 * @Copyright VTN Limited. All rights reserved.
 */
@Configuration
@MapperScan(basePackages = {
        "com.idanchuang.cms.server.infrastructure.persistence.abmau.mapper",
        "com.idanchuang.resource.server.infrastructure.persistence.abmau.mapper",
        "com.idanchuang.cms.server.infrastructure.adcontentservice",
        "com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.abmau.mapper"},
        sqlSessionFactoryRef= "abmSqlSessionFactoryBean")
public class AbmauDatasourceConfig {

    @Value("${spring.abmau.datasource.hikari.url}")
    private String abmauUrl;

    @Value("${spring.abmau.datasource.hikari.username}")
    private String abmauDbUsername;

    @Value("${spring.abmau.datasource.hikari.password}")
    private String abmauPassword;

    @Value("${spring.abmau.datasource.hikari.pool-name}")
    private String poolName;

    @Value("${spring.abmau.datasource.hikari.maximum-pool-size}")
    private Integer abmMaxActive;

    @Value("${spring.abmau.datasource.hikari.minimum-idle}")
    private Integer abmMinIdle;

    @Value("${spring.abmau.datasource.hikari.max-lifetime}")
    private Integer abmLifeTime;

    @Value("${spring.abmau.datasource.hikari.idle-timeout}")
    private Integer idleTimeout;

    @Value("${spring.abmau.datasource.hikari.connection-timeout}")
    private Integer connectTimeout;

    //配置mapper的扫描，找到所有的mapper.xml映射文件
    @Value("classpath*:/mapper/abmau/*.xml")
    private String abmMapperLocations;

    @Bean("abmDataSource")
    public DataSource abmDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(abmauUrl);
        hikariDataSource.setUsername(abmauDbUsername);
        hikariDataSource.setPassword(abmauPassword);
        hikariDataSource.setMaximumPoolSize(abmMaxActive);
        hikariDataSource.setMinimumIdle(abmMinIdle);
        hikariDataSource.setPoolName(poolName);
        //连接的最大生命周期,单位分钟
        hikariDataSource.setMaxLifetime(MINUTES.toMillis(abmLifeTime));
        //空闲时间，单位分钟
        hikariDataSource.setIdleTimeout(MINUTES.toMillis(idleTimeout));
        //连接超时时间，单位秒
        hikariDataSource.setConnectionTimeout(SECONDS.toMillis(connectTimeout));
        return hikariDataSource;
    }

    @Bean("abmSqlSessionFactoryBean")
    public MybatisSqlSessionFactoryBean abmSqlSessionFactoryBean() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(abmDataSource());
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources(abmMapperLocations);
        sqlSessionFactoryBean.setMapperLocations(resources);
        Interceptor[] plugins = {new PaginationInterceptor()};
        sqlSessionFactoryBean.setPlugins(plugins);
        return sqlSessionFactoryBean;
    }

    @Bean("abmTransactionManager")
    public DataSourceTransactionManager abmTransactionManager() {
        return new DataSourceTransactionManager(abmDataSource());
    }
}
