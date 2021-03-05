# Video Calling Jitsi
Simple video call app using jitsi SDK with Firebase to sign in and sign up in the app.
<br>
<div><img src="https://github.com/Ibrahim-Mushtaha/Video_Calling_jitsi/blob/master/app/src/main/res/drawable/is_preview_image.jpg" alt="Italian Trulli"></div>
<h2><a id="user-content--features" class="anchor" aria-hidden="true" href="#-features"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a><a id="user-content--features" href="#-features"></a><g-emoji class="g-emoji" alias="sparkles" fallback-src="https://github.githubassets.com/images/icons/emoji/unicode/2728.png">🕹</g-emoji> Technologies used:</h2>

<ul>
<li>100% Kotlin</li>
<li>MVVM architecture</li>
<li>Android architecture components and Jetpack</li>
<li>Single activity</li>
<li>Kotlin Coroutines</li>
</ul>

# ✨ Features:
<ul>
<li>Sign in & Sign up using firebase_auth</li>
<li>update user status ("online/offline")</li>
<li>one to one video call</li>
<li>one to one voice call</li>
</ul>


<h2><a id="user-content-soon-new-features-and-bugs-will-be-fixed-on-the-next-update-very-soon-" class="anchor" aria-hidden="true" href="#soon-new-features-and-bugs-will-be-fixed-on-the-next-update-very-soon-"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a><g-emoji class="g-emoji" alias="soon" fallback-src="https://github.githubassets.com/images/icons/emoji/unicode/1f51c.png">🎉</g-emoji> Getting Started</em> :</h2>

<ul>
<li>
<h2><a id="user-content-android-architecture-components" class="anchor" aria-hidden="true" href="#android-architecture-components"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a>Android architecture components</h2>
<pre><code>    
dependencies {  
             // navigation
             implementation "androidx.navigation:navigation-fragment-ktx:2.3.2"
             implementation "androidx.navigation:navigation-ui-ktx:2.3.2"

             // ViewModel and LiveData
             implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
             implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
    }
</code></pre>
</li>

<li>
<h2><a id="user-content-android-architecture-components" class="anchor" aria-hidden="true" href="#android-architecture-components"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"><img width="3%%" src="https://emojis.slackmojis.com/emojis/images/1533724346/4435/firebase.png?1533724346"></path></svg></a> Firebase</h2>
<pre><code>    
dependencies {  
             //Declare firebase autth, cloud messaging
             implementation platform('com.google.firebase:firebase-bom:26.2.0')
             implementation 'com.google.firebase:firebase-auth:20.0.1'
             implementation 'com.google.firebase:firebase-messaging:21.0.1'
    }
</code></pre>
</li>

<li>
<h2><a id="user-content-android-architecture-components" class="anchor" aria-hidden="true" href="#android-architecture-components"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"><img width="25" src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Logo_Jitsi.svg/256px-Logo_Jitsi.svg.png"></path></svg></a> Jstie</h2>
<p>The repository typically goes into the build.gradle file in the root of your project:</p>
<pre><code>    
dependencies {  
             allprojects {
               repositories {
                   google()
                   jcenter()
                   maven {
                       url "https://github.com/jitsi/jitsi-maven-repository/raw/master/releases"
                   }
               }
           }
    }
</code></pre>
<p>Dependency definitions belong in the individual module build.gradle files:</p>
<pre><code>           dependencies {
               //jitsi sdk
               implementation ('org.jitsi.react:jitsi-meet-sdk:2.+') { transitive = true }
           }
</code></pre>
</li>

</ul>
