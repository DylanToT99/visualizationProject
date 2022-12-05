//处理业务逻辑的中间件,读取某个json文件的数据
const path = require('path')
const fileUtils = require('../util/file_util')
module.exports = (async (ctx, next) => {
    const url = ctx.request.url
    let filePath = url.replace('/api', '')
    filePath = '../data' + filePath + '.json'
    filePath = path.join(__dirname, filePath)
    try {
        const ret = await fileUtils.getFileJsonData(filePath)
        ctx.response.body = ret
    } catch (error) {
        const errMessage = {
            message: '读取文件失败',
            status: 404
        }
        ctx.response.body = JSON.stringify(errMessage)
    }
    // console.log(filePath);
    await next()
})