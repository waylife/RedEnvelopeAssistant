# RedEnvelopeAssistant
RedEnvelopeAssistant
Below is a chinese instruction:  
[For English](/README_EN.md)

---
#红包助手
---
**本程序后续停止维护**  

完全免费开源（MIT协议）的抢红包软件

做这个软件纯粹是发现Android的模拟点击十分好玩，然后顺道写了一个  
有此基础，可以再扩展其他的很多模拟点击程序

2015春节过去了，2016还会远吗？  
赶紧收藏吧，等着春节直接用，嘿嘿

##功能
此软件是为了辅助抢红包而开发  
支持支付宝以及微信，目前只有Android版本，暂时不考虑开发其他版本  
欢迎移植开发其他版本  
软件支持简繁英三种语言

###支付宝红包助手
支持所有Android版本以及几乎所有机型  
可以在未抢到红包的情形下再抢一次或者多抢几次红包  
目前可以稳定使用  

###微信红包助手
可以在聊天页面自动抢红包  
目前仅支持**Andorid4.4以及以上系统**,部分手机可能无法使用

##下载以及使用

下载      https://github.com/waylife/RedEnvelopeAssistant/tree/master/APK  

演示视频      https://github.com/waylife/RedEnvelopeAssistant/tree/master/Video
###微信红包助手
首先打开微信，然后打开红包助手，按照提示打开红包助手服务，按下Home键，回到桌面，程序会自动运行（运行过程中请保持屏幕常亮）。


###支付宝红包助手
由于支付宝的数据是保存在本地，如果某一次没有抢到红包，可以把应用数据清除，重新登录，然后再抢一次，当然在服务端红包应该是有上限限制的，但是如果第一次没有抢到，可以利用这个再抢一次，直到抢到为止。  
另外一种方式就是改系统时间，此应用没有实现  

##反馈与建议
欢迎反馈bug以及提出改进建议  
有任何使用方面的问题也可以提出
https://github.com/waylife/RedEnvelopeAssistant/issues/new
或者
https://github.com/waylife/RedEnvelopeAssistant/issues/2  

##已知问题
###微信红包助手
 1. 对Android版本有限制，4.4功能全一些  
 2. 抢到红包后需要关闭服务才能查看红包额度  
 3. 自动抢红包模式下无法进行聊天


##技术实现
###支付宝红包助手
1. 直接跳转到应用清除页面
2. root权限使用"am clear XXXX"命令清除应用数据

当然这个清除数据也可以使用模拟点击  

###微信红包助手
1. 使用AccessibilityService对程序进行模拟点击
2. 使用系统服务监听应用UI变化  

##编译
###Eclipse
使用Eclipse导入项目即可以  

###Android Studio
使用Android Studio 1.0以上版本导入即可，IDE会自动进行转换  



##参考
1. AccessibilityService http://developer.android.com/intl/zh-cn/reference/android/accessibilityservice/AccessibilityService.html
2. ROOT权限 https://github.com/Stericson/RootTools  
3. 通知点击 http://download.csdn.net/detail/a332324956/8456633
4. Appmall http://app.sogou.com/m
