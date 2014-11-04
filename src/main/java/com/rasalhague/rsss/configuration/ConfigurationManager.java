package com.rasalhague.rsss.configuration;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigurationManager
{
    private final String CONFIG_FILE_PATH = "config.yml";

    private ConfigurationHolder configuration = new ConfigurationHolder();
    private Yaml yaml;

    private ConfigurationManager()
    {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(options);
    }

    public static ConfigurationManager getInstance()
    {
        return ConfigurationManagerHolder.instance;
    }

    public void saveConfig(ConfigurationHolder configurationHolder)
    {
        //FileWriter creates file if it does not exist
        try (FileWriter fileWriter = new FileWriter(new File(CONFIG_FILE_PATH)))
        {
            yaml.dump(configurationHolder, fileWriter);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public ConfigurationHolder loadConfig()
    {
        if (isConfigFileExist())
        {
            try (FileReader fileReader = new FileReader(new File(CONFIG_FILE_PATH)))
            {
                Object load = yaml.load(fileReader);

                //if file empty
                if (load == null)
                {
                    saveConfig(new ConfigurationHolder());
                    load = yaml.load(fileReader);
                }

                if (load.getClass() == ConfigurationHolder.class)
                {
                    return (ConfigurationHolder) load;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            saveConfig(configuration);
        }

        return configuration;
    }

    private boolean isConfigFileExist()
    {
        File f = new File(CONFIG_FILE_PATH);
        if (f.exists() && !f.isDirectory())
        {
            return true;
        }

        return false;
    }

    private static class ConfigurationManagerHolder
    {
        static ConfigurationManager instance = new ConfigurationManager();
    }
}
