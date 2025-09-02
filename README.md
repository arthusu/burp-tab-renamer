# Simple Tab Renamer for Burp Suite

A minimalist Burp Suite extension that automatically renames Repeater tabs with HTTP method and path for better organization.

![Burp Suite Extension](https://img.shields.io/badge/Burp%20Suite-Extension-orange)
![Java](https://img.shields.io/badge/Java-11+-blue)
![License](https://img.shields.io/badge/License-MIT-green)

## ✨ Features

- 🎯 **Simple Context Menu**: Right-click on any request in Repeater → "Rename Tab"
- 🔄 **Automatic Extraction**: Extracts HTTP method and path from selected requests
- 📝 **Clean Naming**: Renames tabs with format like "POST /plans", "GET /users", etc.
- ⚡ **Lightweight**: Only ~120 lines of clean code
- 🛡️ **Reliable**: No complex dependencies or hacky solutions

## 🖼️ Screenshots

### Before
```
Tab: 17    Tab: 23    Tab: 45
```

### After
```
POST /plans    GET /users    DELETE /items
```

## 🚀 Installation

### Option 1: Download Pre-built JAR
1. Download [`RepeaterTabRenamer-MENU-ONLY.jar`](releases/RepeaterTabRenamer-MENU-ONLY.jar)
2. Open Burp Suite → Extensions → Add
3. Select the downloaded JAR file
4. ✅ Extension loaded!

### Option 2: Build from Source
```bash
git clone https://github.com/arthusu/burp-tab-renamer.git
cd burp-tab-renamer
mvn clean package
```
The compiled JAR will be in `target/repeater-tab-renamer-1.0-jar-with-dependencies.jar`

## 📋 Usage

1. **Open Repeater** with any HTTP request
2. **Right-click** anywhere on the request
3. **Select "Rename Tab"** from the context menu
4. ✅ **Tab renamed automatically** with method + path!

![Usage Demo](docs/usage-demo.gif)

## 🔍 How It Works

The extension:
1. Registers a context menu item for the Repeater tool
2. Extracts HTTP method and path from the selected request
3. Renames the active tab with the format: `{METHOD} {PATH}`
4. Truncates long names (>25 chars) with "..." for readability

## 📊 Supported HTTP Methods

- ✅ GET
- ✅ POST  
- ✅ PUT
- ✅ DELETE
- ✅ PATCH
- ✅ HEAD
- ✅ OPTIONS

## 🛠️ Development

### Prerequisites
- Java 11+
- Maven 3.6+
- Burp Suite Professional/Community

### Project Structure
```
src/
├── main/
│   ├── java/com/burpextension/
│   │   └── RepeaterTabRenamerSimple.java
│   └── resources/META-INF/
│       └── MANIFEST.MF
├── pom.xml
└── README.md
```

### Build Commands
```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Create JAR
mvn package

# Clean build
mvn clean package
```

## 🔧 Configuration

No configuration required! The extension works out of the box.

## 📝 Logging

The extension logs helpful information to Burp's output:

```
Simple Tab Renamer loaded!
🎯 Usage: Right-click on any request in Repeater -> 'Rename Tab'
✅ Automatically extracts method+path from selected request
📝 Example: 'POST /plans', 'GET /users', etc.

🎝️ Right-click 'Rename Tab' activated!
✅ Extracted from selected request: POST /plans
✅ Menu rename successful: POST /plans
```

## 🤔 FAQ

**Q: Does it work with all Burp Suite versions?**  
A: Tested with Burp Suite 2023.x using the Montoya API.

**Q: Can I rename multiple tabs at once?**  
A: Currently, it renames one tab at a time. Right-click → Rename Tab for each.

**Q: What if the path is very long?**  
A: Paths longer than 25 characters are truncated with "..." for readability.

**Q: Does it support keyboard shortcuts?**  
A: No, this version focuses on simplicity with menu-only interaction.

## 🐛 Troubleshooting

### Tab not renaming?
- Ensure you're in the Repeater tool
- Right-click directly on a request
- Check Burp's output for error messages

### Extension not loading?
- Verify Java 11+ compatibility
- Check the JAR file isn't corrupted
- Look at Burp's extension error messages

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [PortSwigger](https://portswigger.net/) for the excellent Burp Suite platform
- [Burp Montoya API](https://portswigger.github.io/burp-extensions-montoya-api/) documentation

## 📞 Support

- 🐛 [Report Issues](https://github.com/arthusu/burp-tab-renamer/issues)
- 💬 [Discussions](https://github.com/arthusu/burp-tab-renamer/discussions)
- ⭐ Don't forget to star the repo if it helped you!

---

**Made with ❤️ for the Bug Bounty and Penetration Testing community**
