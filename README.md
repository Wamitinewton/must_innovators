# Configuring `keys.properties` for MeruInnovators Android Project

## Overview
This guide provides instructions on how to set up the `keys.properties` file to properly configure the backend URL and signing keys for the MeruInnovators Android application. The `keys.properties` file is used to store sensitive configuration details outside of the source code to maintain security and allow for easy local development and deployment.

## Why `keys.properties`?
- Keeps sensitive information (such as API keys and signing credentials) out of version control.
- Ensures that contributors can configure their own environment without affecting others.
- Prevents accidental exposure of security credentials in public repositories.

## Steps to Configure `keys.properties`

### 1. Create the `keys.properties` File
Navigate to the root directory of the project and create a new file named `keys.properties` (if it doesn't exist already).

### 2. Add Required Properties
Copy and paste the following properties into your `keys.properties` file and replace the placeholder values with the actual values you intend to use:

```properties
# Backend API URL
BACKEND_URL=https://your-backend-url.com

# Release signing configuration
RELEASE_STORE_FILE=keystore.jks
RELEASE_STORE_PASSWORD=your_keystore_password
RELEASE_KEY_ALIAS=your_key_alias
RELEASE_KEY_PASSWORD=your_key_alias_password
```

### 3. Explanation of Properties
- `BACKEND_URL`: The base URL for the backend API.
- `RELEASE_STORE_FILE`: Path to the keystore file used for signing the application.
- `RELEASE_STORE_PASSWORD`: Password to unlock the keystore file.
- `RELEASE_KEY_ALIAS`: Alias name for the key within the keystore.
- `RELEASE_KEY_PASSWORD`: Password for the key alias.

### 4. Ensure the File is Ignored in Version Control
To prevent accidental commits of sensitive information, make sure `keys.properties` is included in the `.gitignore` file:

```gitignore
keys.properties
```

### 5. How the File is Used in `build.gradle.kts`
The `keys.properties` file is loaded in the `build.gradle.kts` script using the `Properties` class:

```kotlin
val properties = Properties()

try {
    val keystoreFile = rootProject.file("keys.properties")
    if (keystoreFile.exists()) {
        properties.load(keystoreFile.inputStream())
    } else {
        throw GradleException("keys.properties file not found")
    }
} catch (e: Exception) {
    logger.warn("Warning: ${e.message}")
}
```

Then, the values are accessed and assigned to necessary configurations, such as:

```kotlin
val backendUrl = properties.getProperty("BACKEND_URL")
    ?: throw GradleException("BACKEND_URL not found in keys.properties")

buildConfigField("String", "BACKEND_URL", "\"$backendUrl\"")
```

Similarly, the signing configuration is set as follows:

```kotlin
@file:Suppress("UNREACHABLE_CODE")

signingConfigs {
  create("release") {
    val keystoreFile = properties.getProperty("RELEASE_STORE_FILE")
      ?: throw GradleException("store file not found in keys.properties")
    val keystorePassword = properties.getProperty("RELEASE_STORE_PASSWORD")
      ?: throw GradleException("store password not found in keys.properties")
    val keyAlias = properties.getProperty("RELEASE_KEY_ALIAS")
      ?: throw GradleException("key alias not found in keys.properties")
    val keyAliasPassword = properties.getProperty("RELEASE_KEY_PASSWORD")
      ?: throw GradleException("alias password not found in keys.properties")

    storeFile = file(keystoreFile)
    storePassword = keystorePassword
    keyAlias = keyAlias
    keyPassword = keyAliasPassword
  }
}
```

### 6. Troubleshooting
- **Error: `keys.properties file not found`**  
  Ensure that `keys.properties` is present in the root directory.
- **Error: `Property XYZ not found in keys.properties`**  
  Verify that the required property exists and is correctly formatted.
- **Error: `Incorrect keystore or password`**  
  Check if the keystore file exists and the credentials are correct.

## Conclusion
By properly setting up the `keys.properties` file, contributors can securely configure their local development environment and ensure smooth builds. This setup helps maintain security and project integrity while allowing flexibility in managing sensitive credentials.

