Introduction
============

Background
----------

Mobile platforms contain a large variety of sensors which can provide contextual information on user's activities. Although inferring user activities from sensor aggregates is not a new concept, previous research on the topic has generally been limited to one to two sensor experiments. Several studies show how accelerometers can be used to infer coarse-grained information such as user motion (walking, standing, sitting, running, etc) and others show how it is possible to achieve even finer granularities such as infering key-strokes and other touch patterns. Changing perspective of what constitues a "sensor", modern *virtual sensors* combine multiple hardware sensors to improve sensor readings or create entirely new sensor capabilities. For example, the Android Linear Acceleration Sensor builds upon the acccelerometer and is *triggered* whenever a significant change in motion occurs. 


GPS and other Wireless interfaces have, for several years, provided robust contextual inference channels. Many applications are senstive to *location context*. In one situation, users can search for nearby shops. In another situation, marketing firms can gather information on clients shopping habits, which often help in building predictive market models. 

It should be further noted that adding context to applications opens up a wide variety of privacy concerns. Context is, in a way, a side channel by nature.

Cal, a Context Aware Lifestyle
------------------------------

Cal, or Context Aware Lifestyle, is a multimodal sensor aggregation platform that is aware of its users context. At the surface, Cal is a project management system. Once a user starts or joins a registered project, Cal will automagically track, tag, and index user activity while inside the project context. In other words, the system will manage project bookmarks, Google Docs, web search history, and emails. Ultimately, a user should be able to ask Cal, "Ok Cal, show me everything from my meeting with Mohit last Tuesday." 

Ideally, Cal should be relatively transparent to the user. The system should determine with reasonable confidence that a user has joined or left a project context with minimal user interaction. This way, the user is focused on their project and not Cals interface. Cals presence will be discussed in more detail later as well as potential machine learning pipelines. 

To conserve power, Cal constrains most of its sensor services to run only while a project is active. In this sense, Cal is contextually dependent on activity. The system has several layers of wake-up states inherent in the protocol. 

.. figure:: images/graphs/wakeup_fsm.png

	Simplified state machine of Cal Network service discovery. Some logic missing for clarity. Double circles mean fork as background process. Note, NSD scan has additional logic limiting its mobility to project scanning while currently in a project context. Most of the sensors are active in the Project Context only.


