package com.doublecat.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import javax.annotation.Generated;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Zongmin
 * @Date Create in 2021/7/31 11:32
 * @Modified By:
 */
public class Generator {
    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();

        ConfigurationParser cp = new ConfigurationParser(warnings);
        InputStream rs = Generator.class.getClassLoader().getResourceAsStream("generatorConfig.xml");
        Configuration config = cp.parseConfiguration(rs);

        // Configuration config = cp.parseConfiguration(ClassLoader.getSystemResourceAsStream("mybatis-generator.xml"));

        DefaultShellCallback callback = new DefaultShellCallback(true);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

        myBatisGenerator.generate(null);

        for (String warning : warnings) {

            System.out.println(warning);

        }
    }
}
