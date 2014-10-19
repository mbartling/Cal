Cal System
==========

From here on, imagine Cal as two parts: 

#. The User interface
#. A semi-transparent wrapper for existing Google Services

Obviously, the bulk of Cal is in 2.


Main Subsystems
---------------

NSD, Network Service Discovery
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Network Service discovery as described by the Android API.

	"Adding Network Service Discovery (NSD) to your app allows your users to identify other devices on the local network that support the services your app requests. This is useful for a variety of peer-to-peer applications such as file sharing or multi-player gaming. Android's NSD APIs simplify the effort required for you to implement such features."


Since Cal, by default, should be interacting with Gmail, Google Docs, and a Web Browser we can assume that there is a network connection available. NSD also assumes an active network connection, whereas other service detection methods (e.g. WiFi Direct P2P) do not. Therefore, I will use NSD for simplicity.

As previously mentioned: To conserve power, Cal constrains most of its sensor services to run only while a project is active. In this sense, Cal is
contextually dependent on activity. The system has several layers of wake-up states inherent in the protocol.


Nth-Sense
^^^^^^^^^

Sensor readings need to occur in the background, i.e. Activity/fragment independent. I will design a scheduled background service to manage sensor events. These sensor readings will be temporarily saved to an application local database or bin buffer. The system will pre-process this data and then notify the Cal system that a Data Package is ready for post processing/handling.


Context Detection System Machine Learning
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The final machine learning pipeline is heavily dependent on what sensors I use, and how fine a granularity I wish to make the Context Detection System. I expect to use a combination of Support Vector Machines (SVMs) and Hidden Markov Models (HMMs) for the actual context prediction. Also, I may consider using genetic algorithms to help decide the optimum ML pipeline for a particular user. Again, this all depends on time constraints and what the data looks like. See Cal Time Line for more information.


Cal Remote Server and Database Interface
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

For privacy reasons, Cal will only keep users personal information (bookmarks, project contacts, etc) saved locally on the app or within Google's secure backup service. 

However, I will still need to design a network interface for a remote database/server. This remote entity will handle most of the heavy ML computations and data management. The remote server will determine the optimal ML algorithm and parameters on a per-user basis, serialize these methods, and transmit them to the Cal Application. This way, at least latency critical Cal methods (e.g. walking away from a project) will be transparent to the user.


User Interface
^^^^^^^^^^^^^^

For the most part, Cal should behave transparently and automatically to the user. The user still has the option to manually create a project or change projects. however, Cal should do its best to pick the correct project to join. Otherwise, if Cal is unsure of which project to join, or even if the user would like to join the project, Cal should inform the user and provide a filtered list of highly probable projects. 



Voice Recognition (Optional)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

It turns out that adding additional voice capabilities to either Google Now or a Custom in-app method is fairly simple. If time permits, I will try to add this feature. Voice capabilities enhance the user experience, which may make the app more popular. However, adding this feature is fluff and not completely necessary.


Cal Time line
-------------

