# Paasmer Cloud SDK for Android

The Paasmer Cloud SDK  for android is an easy-to-use wrapper for the Paasmer REST API, providing a clear, type-safe way for your Android app to interact with connected products, all via the Particle Cloud.
SDK features include:
- User sign up and sign in.
- Getting a list of a devices.
- Reading information on devices
- Controlling devices.
- Publishing events from mobile devices, and subscribing to events published by devices & apps

The complete SDK sources are available to browse on GitHub, and can be downloaded as a zip file.

For usage info, check out the app module included in the git repository.

###### Beta notice
While this SDK is ready for production use, it is still under development and is currently in beta. The API is mostly stable, but may be subject to further changes prior to leaving beta. Once the SDK leaves beta, the API should never change outside of "major" version updates.

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
        compile 'com.github.PaasmerIoT:Android-Cloud-SDK:1.0'
}
	
```




