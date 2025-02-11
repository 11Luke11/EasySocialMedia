# EasySocialMedia Plugin

![Version](https://img.shields.io/badge/Version-1.3-green.svg)  
![License](https://img.shields.io/badge/License-GPLv3-blue.svg)

EasySocialMedia is a lightweight and customizable Minecraft plugin designed to help server admins share social media links and other important information with players through simple commands.

---

## **Features**
- **Customizable Commands**: Add, remove, or modify commands to share social media links, websites, or any other messages.
- **Permission Support**: Control access to commands with customizable permissions.
- **Dynamic Placeholders**: Use placeholders like `{main}` and `{social}` to dynamically insert command names in messages.
- **Color Codes**: Style your messages with Minecraft color codes for a visually appealing experience.
- **Easy Configuration**: Simple YAML-based configuration and messages files for quick setup.

---

## **Installation**
1. Download the latest `.jar` file from the [Releases](https://github.com/11Luke11/EasySocialMedia/releases) page.
2. Place the `.jar` file into your server's `plugins` folder.
3. Restart your server to generate the configuration files.
4. Customize the `config.yml` and `messages.yml` files to suit your needs.
5. Reload the plugin using `/esm reload` or restart your server.

---

## **Configuration**
The plugin uses two main configuration files: `config.yml` and `messages.yml`. You can find detailed explanations and examples in the following files:
- **[config.yml](src/config.yml)**: Controls commands, permissions, and plugin settings.
- **[messages.yml](src/messages.yml)**: Contains all the plugin's messages and placeholders.

---

## **Commands**

### **Admin Commands**
- **`/{main} help`**: Displays the help menu.
- **`/{main} reload`**: Reloads the configuration and messages files.

### **User Commands**
- **`/{social}`**: Displays the social media links menu.

---

## **Permissions**
- **`easysocialmedia.use`**: Allows players to use the social media command.
- **`easysocialmedia.admin`**: Grants access to admin commands like `/esm reload`.

---

## **Support**
If you encounter any issues or have questions, feel free to:
- Open an issue on the [GitHub Issues](https://github.com/11Luke11/EasySocialMedia/issues) page.
- Join our [Discord server](https://discord.gg/QYgnYFfvQw) for support.

---

## **Contributing**
Contributions are welcome! If you’d like to contribute to EasySocialMedia, please:
1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Submit a pull request with a detailed description of your changes.

---

## **License**
EasySocialMedia is licensed under the **GNU General Public License v3.0**.  
For more details, see the [LICENSE](LICENSE) file.

---

## **Credits**
- Developed by **[MrInfern](https://github.com/11Luke11)**.
- Inspired by the need for a simple and customizable social media plugin for Minecraft servers.
