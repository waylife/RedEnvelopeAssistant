# RedEnvelopeAssistant
RedEnvelopeAssistant
Below is a chinese instruction:  

---
#红包助手
---
完全免费开源的抢红包软件  
欢迎各路大神完善改进  
QQ交流群：101169497   
   
做这个软件纯粹是发现Android的模拟点击十分好玩，然后顺道写了一个   
有此基础，可以再扩展其他的很多模拟点击程序  

##功能
此软件是为了辅助抢红包而开发  
支持支付宝以及微信，目前只有Android版本，暂时不考虑开发其他版本  
由于只是业余2-3天时间做的，可能有不少功能问题 
需要的同学可以自行扩展功能   
春节期间不再维护，等春节过后  

###支付宝红包助手
支持所有Android版本以及几乎所有机型  
可以在未抢到红包的情形下再抢一次或者多抢几次红包  
目前可以稳定使用  
###微信红包助手
可以在聊天页面自动抢红包  
目前仅支持**Andorid4.4以及以上系统**   
    
##下载以及使用

下载  https://github.com/waylife/RedEnvelopeAssistant/tree/master/APK   
演示视频  https://github.com/waylife/RedEnvelopeAssistant/tree/master/Video   
###微信红包助手
按照提示开启红包服务   
进入设置页面参照提示进行操作  
###支付宝红包助手
由于支付宝的数据是保存在本地，如果某一次没有抢到红包，可以把应用数据清除，重新登录，然后再抢一次，当然在服务端红包应该是有上限限制的，但是如果第一次没有抢到，可以利用这个再抢一次，直到抢到为止。  
另外一种方式就是改系统时间，此应用没有实现  

##已知问题
###微信红包助手
 1. 对Android版本有限制，4.4功能全一些  
 2. 自动抢红包可能会重复抢一个红包  
 3. 抢红包过程中页面跳转会有问题  
 4. 抢到红包后需要关闭服务才能查看红包额度  
 

##技术实现
###支付宝红包助手
1. 直接跳转到应用清除页面
2. root权限使用"am clear XXXX"命令清除应用数据  

###微信红包助手 
1. 使用AccessibilityService对程序进行模拟点击
2. 使用系统服务监听应用UI变化  

##编译
使用Eclipse导入项目即可以   


##后续
1. 修复bug
2. 代码结构优化
3. UI权限以及包监听优化 
4. 等


##参考
1. ROOT权限 https://github.com/Stericson/RootTools  
2. 通知点击 http://download.csdn.net/detail/a332324956/8456633
3. Appmall http://app.sogou.com/m 
