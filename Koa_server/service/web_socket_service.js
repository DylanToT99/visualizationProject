const path=require('path')
const fileUtils=require('../util/file_util')
const WebSocket=require('ws')
//创建webSokcet服务端的对象,绑定的端口号是9998
const wss=new WebSocket.Server({
    port: 9998
})

//服务端开启了监听
module.exports.listen=()=>{
//对客户端的连接事件进行监听
//client: 代表的是客户端的连接socket对象
wss.on('connection',client=>{
    console.log('有客户端连接成功了');
    //对客户端的连接对象进行message事件的监听
    //msg: 由客户端发给服务端的数据
    client.on('message',async msg=>{
        console.log('客户端发送数据给服务端了: '+msg)
        let payload=JSON.parse(msg)
        const action =payload.action
        if(action==='getData'){
            let filePath='../data/'+payload.chartName+'.json'
           filePath=path.join(__dirname,filePath)
           const res=await fileUtils.getFileJsonData(filePath)
           payload.data=res
           client.send(JSON.stringify(payload))
        }else{
            //原封不动将接受到的数据转发给每一个处于连接状态的客户端
            // wss.clients  所有客户端的数据
            wss.clients.forEach(client=>{
                client.send(msg.toString('utf-8'))
            })
        }
        
    })
})
}
