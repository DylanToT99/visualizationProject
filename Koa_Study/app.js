//创建koa对象
const Koa = require('koa')
const app = new Koa()
//编写响应函数(中间件)
//context: 代表上下文, web容器, 可以context.request   context.resopnse
//next: 下一个中间件 下一层中间件是否能够得到执行, 取决于next这个函数有没有被调用
app.use((context, next) => {
    console.log('第一层中间件');
    context.response.body = 'hello world'
    next()
    console.log('第一层中间件.....');
})
//第二层中间件
app.use(async (ctx, next) => {
    console.log('第二层中间件');
    const ret = await next()
    console.log(ret);
    console.log('第二层中间件.......');
})
//第三层中间件
app.use((ctx, next) => {
    console.log('第三层中间件');
    return 'I LOVE DOG'
    // next()
})
//绑定端口号
app.listen(3000)
