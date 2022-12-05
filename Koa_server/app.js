//1.创建KOA的实例对象
//2.绑定中间件
//3.绑定端口号  8888
const Koa = require('koa')
const respDuration = require('./middleware/koa_response_duration')
const app = new Koa()
app.use(respDuration)
const respHeader = require('./middleware/koa_resopnse_header')
app.use(respHeader)
const respData = require('./middleware/koa_resopnse_data')
app.use(respData)
app.listen(8887)

const webSocketService=require('./service/web_socket_service')
//开启服务端的监听
//
webSocketService.listen()