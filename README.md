# MKR-ANDROID-LIB-UI

#	Project Level Gradle
		repositories {
			maven {
				url "https://api.bitbucket.org/1.0/repositories/THEMKR/android-lib-ui/raw/releases"
				credentials {
					username 'THEMKR'
					password '<PASSWORD>'
				}
			}
		}

#	APP Level Gradle
		implementation 'com.lory.library:ui:1.0.1'
		implementation 'com.android.support:recyclerview-v7:28.0.0'
        implementation 'com.android.support:appcompat-v7:28.0.0'
        implementation 'com.android.support:customtabs:28.0.0'