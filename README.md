## App automation personal boilerplate for iOS and Android using Appium, Kotlin, Gradle, Allure, and Jenkins

### Project Structure

```tree

├── main # pages and helpers
│   ├── kotlin
│   │   ├── android
│   │   │   ├── AndroidPageObjectManager.kt
│   │   │   ├── AndroidScreenActions.kt
│   │   │   └── AppPage.kt
│   │   ├── apis
│   │   │   └── example.json
│   │   ├── ios
│   │   │   ├── AppPage.kt
│   │   │   ├── IosPageObjectManager.kt
│   │   │   └── IosScreenActions.kt
│   │   └── utilities
│   │       ├── ApiResponseHandler.kt
│   │       ├── AppCenter.kt
│   │       ├── CustomLogger.kt
│   │       ├── Enums.kt
│   │       ├── ReadConfig.kt
│   │       ├── ReadEnv.kt
│   │       ├── ScreenActions.kt
│   │       ├── ServerAppium.kt
│   │       └── interface
│   │           └── CommonFunction.kt
│   └── resources # folders for environement and .ipa / .apk
│       ├── api.properties
│       ├── deviceEnvironment.json
│       └── env.properties
└── test # Test cases and Jenkins file
    ├── kotlin
    │   ├── TestBase.kt --> Entry point of the test
    │   ├── android
    │   │   └── TestCaseName.kt
    │   └── ios
    │       └── TestCaseName.kt
    └── resources
        ├── allure.properties
        └── jenkinsfile
            └── SampleJenkins.groovy


```


#### Requirements
- nodeJS 21.16.2
- gradle 8.1.1
- appium 2.4.1
- appium-doctor 1.16.2
- npm 9.7.1
- java 17.0.2 (needs to use this because its compatible with allure report)
- Xcode 15.2
- Android Studio for Android SDK
- Android SDK (IntelliJ)
- allure 2.11.2 (gradle's latest version)
- carthage 
- WebdriverAgent 7.0


#### Install requirements

1. node js - https://nodejs.org/en/download
```bash
node -v
sudo npm install -g npm@9.7.1
```
2. appium version 2.0.1
NOTE: Its important to have this version because there are bugs on the latest version 2.5.1. We need to update the script of ServerAppium.kt if we want to update the appium version
```bash
sudo npm i -g appium@next
appium -v
```
2.1 appium uiautomator driver
```bash
sudo appium driver install uiautomator2
```
2.2 appium xcuitest driver
```bash
sudo appium driver install xcuitest
```
3. appium doctor
```bash
sudo npm install -g appium-doctor
appium-doctor --version
```
4. carthage
```bash
sudo npm i carthage
appium-doctor --version
```
5. java - https://www.oracle.com/java/technologies/downloads/#jdk20-mac
6. appium inspector -  https://github.com/appium/appium-inspector/releases
7. xcode - App Store
8. carthage - https://github.com/Carthage/Carthage/releases
9. Homebrew
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```
10. Gradle
```bash
brew install gradle
gradle --version
```
11. Allure
```bash
brew install allure
```
11. Open webdriveragentrunner on xcode then change the Signing & Capabilities to your team. on WebDriverAgentLib and WebDriverAgentRunner
    `open /Users/<your-computer-name>/.appium/node_modules/appium-xcuitest-driver/node_modules/appium-webdriveragent/WebDriverAgent.xcodeproj`
to test: attached an iphone, choose this phone and run Integration test **_Xcode > Project > Build_**
OR
12. Download the Webdriver agent from Github [https://github.com/appium/WebDriverAgent#readme]. 
Open on Xcode > Choose your phone > Change the Signing & Capabilities to your team. 
Choose the project : WebDriverAgentRunner > Build the project > Product > Test

12. Add the following to PATH  
    If you use zsh in command line, then use this command 
    
```bash
open ~/.zshrc
```
If you use bash in command line, then use this command
```bash
open ~/.bash_profile 
```
or 
```bash
open ~/.bashrc 
```
once you open the file, then add all these commands and save. 
```
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/tools/bin
export JAVA_HOME=$(/usr/libexec/java_home)
export PATH=$PATH:$JAVA_HOME/bin
```
After adding all the commands, close the terminal instance and create a new terminal instance and run "adb" and "java -version" to check if you are able to access these commands
13. copy env.properties to src/main/resources/ if you want to update.
```bash
open -a TextEdit filename 
```

#### Start Appium server
```bash
appium -p 4723 --relaxed-security
```

#### Check iOS device list
```bash
xcrun xctrace list devices
```

#### Check Android device list
```bash
adb devices
```
Note: If you cannot see the device, then you need to enable the developer mode on the phone. Settings > About phone > Tap build number (6x) > Developer options will be enabled > Enable USB Debugging

#### Run using command line
Execute all test
`./gradlew :test`

Execute specific test
`./gradlew :test --tests "<testsuite.name.here>" -Denv=<device.environment.here> -Dlogin=true -DappVersion=<application.version.here>` 
`./gradlew :test --tests "<testsuite.name.here>" -Denv=<device.environment.here> -Dlogin=true -Dticketnumber=<ticket.number.here>` 


#### Run allure report 
1. Run the test
2. Allure result will be created on build/allure-results

```bash
 ./gradlew allureServe
```
---
### Run on Jenkins
#### Install Jenkins
1. Install: Download and install https://www.jenkins.io/download/lts/macos/
2. Open Jenkins > Manage jenkins > System > Change the following

    - Jenkins URL: http://localhost:8080/
    - Global properties > Environment variables > Add the following
        - ANDROID_HOME = /Users/<your-computer-name>/Library/Android/sdk
        - APP_VERSION =  2024.10.0 (example only)
        - PATH+EXTRA = (result from echo $PATH)
3. Install plugins: ANSI, Slack, Git
4. Get ssh public key

```bash
cd .ssh
cat id_rsa.pub
```

6. Add this public key on space preferences > Git Keys > Add SSH Key
7. Copy env.properties from (me) to .jenkins/ file because we have step on pipeline to copy this env.properties to jenkins workspace
8. Setup Slack https://medium.com/appgambit/integrating-jenkins-with-slack-notifications-4f14d1ce9c7a9Add Credentials on Manage Jenkins > Credentials >  Click Global > Add Credentials > Add for Space and Slack


### Before running on real device
1. Make Sure PIN and auto-fill are disabled on both devices
2. For ios, make sure there is no other app on the real device. (need to fix this, for deeplink)
3. For ios, make sure the device os > 16.4 (for deeplink)
4. For iOS, make sure the device is unlocked and should never be locked during the test. Settings > Search for Auto-lock > Never


### Notes:
When executing `adb devices` and devices is unauthorized, just run this command 
```bash
adb kill-server
adb start-server
```

When executing `jenkins` command
```bash
brew services stop jenkins-lts  #Stop Jenkins 
brew services restart jenkins-lts #Restart Jenkins 
```

How to make the jenkins public. (installed using homebrew)
```bash
cd ~/Library/LaunchAgents/     
open -a TextEdit homebrew.mxcl.jenkins-lts.plist #update the host here.  <string>--httpListenAddress=172.29.97.136</string>
launchctl unload homebrew.mxcl.jenkins-lts.plist
launchctl load homebrew.mxcl.jenkins-lts.plist

# There is also this file
cd /usr/local/opt/jenkins-lts
# then update the Jenkins URL on Mange Jenkins > System > Jenkins URL 
```
See running port
```bash
lsof -n -i4TCP:8100
kill -9 PID
```

---

### How to debug
The screenshots are from IntelliJ IDE to debug the test.
1. On `Testbase.kt`, change the following on specific device capability. 
```kotlin
            caps.setCapability("appPackage", "<app.package.name.here>") //Change the identifier of the app you wanted to test (gradle.properties)
```
2. On `deviceEnvironment.json`, change the UDID of the phone you will use and the appPackage type
   ![Alt text](readme_images/howtodebug_config1.png)
3. On the upper right corner, click the `Edit Configurations`, add on Gradle > Add new Configuration then the command to run the test 
```bash
:test --tests "<project.path.here>" -Denv=Ios16_Beta -Dlogin=true
```
4. Run the test


### Device Farm
Install plugins for the device farm environment
```bash
appium plugin install --source=npm appium-device-farm
appium plugin install --source=npm appium-dashboard
```
Run Device Farm 
```bash
appium server -p <port_number> -ka 800 --use-plugins=device-farm,appium-dashboard -pa /wd/hub --plugin-device-farm-platform=both
```
- It will run on the default port 4723, `http://localhost:4723/device-farm/`



### Errors
- `Error: Unable to launch app foreground because the device is locked` - Change iPhone settings. Settings > Search for Auto-lock > Never
- `Error: Phone is not recognized on xcode` - Change iPhone settings. Settings > Developer > Enable UI Automation
- `Could not start a new session. Possible causes are invalid address of the remote server or browser start-up failure. ` - It cannot see the specified appium server, it works if manually start appium server ("appium -p 4723 --relaxed-security") and specified the url 
- `Error installing app. <alpha.app> Failed to receive any data within the timeout: 60000` - Somehow its because of the capabilities  
- `cannot install app ApplicationVerificationFailed` - Check iOS orgId and signingId capabilities  
- Android `unatuhorized device from adb devices command` - Settings > Developer Options > Revoke USB debugging authorizations > Enable again and try again the command `adb devices`  

## API Test
1. Copy api.properties to src/main/resources/ if you want to update.
