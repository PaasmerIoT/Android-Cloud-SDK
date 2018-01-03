# Paasmer Cloud SDK for Android

The Paasmer Cloud SDK  for android is an easy-to-use wrapper for the Paasmer REST API, providing a clear, type-safe way for your Android app to interact with connected products, all via the Paasmer Cloud.
SDK features include:
- User sign up and sign in.
- Create devices.
- Getting a list of a devices.
- Reading information on devices
- Controlling devices.
- Publishing events from mobile devices, and subscribing to events published by devices & apps

The complete SDK sources are available to browse on GitHub, and can be downloaded as a zip file.

For usage info, check out the app module included in the git repository.

## Installation
  To get a Git project into your build:
### Step 1
Add it in your root build.gradle at the end of repositories:
```

allprojects {
repositories {
	...
	maven { url 'https://jitpack.io' }
	}
}
	
```
	
### Step 2

Add the dependency:
	
```

dependencies {
        compile 'com.github.PaasmerIoT:Android-Cloud-SDK:2.0'
}
	
```

### SDK Methods
| name | parameters | Description |
| --- | --- | --- |
| userSignup | username, password, email | signing up into paasmer platform |
| userLogin | username, password | sign in into paasmer platform. authentication token will be generated for later communication |
| verifyEmail | verification code |  |
| createDevice | accesstoken, devicename, sdk, type,bluethooth,wifi | creating your own device |
| editDevice | accesstoken, deviceId, devicename,sdk,type,bluethooth,wifi | modify existing device details |
| deleteDevice | accesstoken, deviceId | delete device from an account |
| getDevices | accesstoken | lists available devices |
| createFeed | accesstoken, device id, feed name, feed type,pin number, pin base,protocol type | create new feed for particular device |



