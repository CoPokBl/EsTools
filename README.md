# EsTools
A general purpose Essentials like plugin with everything you need but not messing up anything (MC 1.0+)

EsTools is a plugin designed to be like EssentialsX in that it provides heaps of 
helpful commands, but unlike essentials it doesn't mess up or override vanilla features (e.g. the give command). 
This plugin doesn't override existing Minecraft commands and therefore is less likely to mess stuff up.
EsTools is also completely `/reload` safe.

Commands:
[View Them In Our Wiki](https://estools.serble.net)

## Installing
Download the latest .jar from our [releases page](https://github.com/CoPokBl/EsTools/releases/). Place
the file in the plugins folder of your server and start it.

## Support
The plugin is designed to work on every public spigot build. Any issues found on legacy versions
should be reported. Due to the size of the task, we can't test every new feature on every version.
If you find a bug, please report it on the [GitHub Issues](https://github.com/CoPokBl/EsTools/issues).

To find out what versions have been tested look at TestedVersions.txt in the project.
We will likely be slow to test new Minecraft versions, but you can probably assume 
that EsTools will be fully functional on new versions.

**We officially support the following:**  
- Bukkit/Spigot/Paper 1.0.0+
- Folia Latest Build

For support DM us at: Discord: @copokbl and @calcilore. Or email support@serble.net.

## Building
To build the plugin you need to have maven installed. You can build with:
```shell
mvn package
```
The plugin will be in the target folder.

Our builds are built against Java 8. We can't guarantee that the plugin will work on Java 7 or below.
Language features from Java 8 are used in the plugin.

For our latest builds check out our [Jenkins instance](https://ci.serble.net/job/EsTools/).

## Contributing
We are open to contributions. If you want to contribute to the project, please fork the repository and make a pull request.

**DO NOT CONTRIBUTE CODE WITH JAVA 9+ FEATURES**

## Testing
We have a builtin testing system for commands. It isn't fully autonomous and will still require you to 
verify that the commands were successful, although it will report any exceptions that occur in commands.  

Start the test with `/estools test`. Read the chat output as it goes through and runs every command, 
sometimes it may `/msg` you and ask you to do something. In general don't switch item slots 
and only move for the commands `/fly`, `/flyspeed` and `/walkspeed`.

## Metrics
EsTools uses [bStats metrics](https://bstats.org) to collect anonymous usage data.
View the front page of [our wiki](https://estools.serble.net/) for information
on opting out.
![EsTools bStats statistics](https://bstats.org/signatures/bukkit/EsTools.svg)
