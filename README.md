# Pets

This is a simple spigot plugin that allows players to buy and spawn their own pets of which each one has it's own effect which is being applied to nearby players. It uses the [Vault](https://dev.bukkit.org/projects/vault) plugin to communicate with the used money plugin. Everything can be fully customized in the config as well as other languages.

## Features

- Advanced GUI
- 6 available pets (more coming soon)
- MySQL support
- Fully customizable
- Multiple languages

<img src="/images/2020-11-29_01.29.20.png" width="400">
<img src="/images/2020-11-29_01.30.23.png" width="400">

## Installation

**Important**! Please note that this plugin requires the [Vault](https://dev.bukkit.org/projects/vault) plugin and a compatible money plugin to work properly!

1. Download the latest JAR file from the [releases tab](https://github.com/VoxCrafterLP/Pets/releases).
2. Move the JAR file into your plugins folder.
3. Restart your server.
4. Check the console to ensure that the plugin detected Vault and is working properly.
5. Locate and open the config file in `/plugins/Pets/config.yml`.
6. Configure the plugin to your needs.
7. If you plan to use a MySQL database, enter the credentials
8. Have fun!

## Config
### worlds-disabled

Here you can configure worlds in which players shouldn't be allowed to spawn pets.

**Example**:
```yaml
worlds-disabled:
  - world_the_nether
  - world_the_end
  ```
  
### pets

Here you can enable and disable pets and modify their price.

**Example**:
```yaml
pets:
  ChickenPet:
    price: 500
    enabled: true
  ```

### admin-permission

Here you can set a custom permission for use in a permissions plugin.

### pet-radius

Here you can set the radius in which the effects of a pet should be applied.

### pet-invulnerable

Here you can set if the pets should be able to take damage or not.

### random-color

If enabled, the pets get a random colored name when spawning them.

### language-file

Here you can configure the language which should be used by the plugin.

**Example**:
```yaml
language-file: en_US
```
**Available Locales**
- en_US (American English)
- de_DE (German)
- de_CH (Swiss German)

## Commands

### /pet
Opens the pet gui.

### /pet give
**Syntax:** /pet give <player> <pet>

**Permission:** defaulted to `pets.admin` (can be changed in the config)

## License
This project is licensed under the GNU GPL v3 and may be used accordingly. Further information can be found [here](https://github.com/VoxCrafterLP/Pets/blob/master/LICENSE).
