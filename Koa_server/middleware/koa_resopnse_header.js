//设置响应头的中间件
module.exports = (async (ctx, next) => {
    const contentType = 'application/json;'
    ctx.set('Content-Type', contentType)
    ctx.set("Access-Control-Allow-Origin", "*")
    ctx.set('Access-Control-Allow-Methods', 'OPTIONS,GET,PUT,GET,POST,DELETE')
    // ctx.response.body='{"success:" true}'
    await next()
})