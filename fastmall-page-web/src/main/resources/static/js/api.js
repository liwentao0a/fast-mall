var protocol_host={
    page:'http://localhost:6001',
    file:'http://localhost:6011',
    user:'http://localhost:8001',
    passport:'http://localhost:8011',
    product:'http://localhost:8021',
    order:'http://localhost:8031',
}

var code={
    //--------------------请求成功--------------------
    SUCCESS:1,
    RETURN_FALSE:2,

    //--------------------请求失败--------------------
    // 参数错误
    PARAMS_IS_NULL:10000,
    PARAMS_TYPE_ERROR:1003,

    // 数据错误
    DATA_NOT_FOUND:20000,
    DATA_IS_WRONG:20001,

    // 权限错误
    PERMISSION_NO_ACCESS:30000,
}

function baseAjax(opt,options){
    Utils.prototype.objectPutAttrByObject(opt,{xhrFields: {withCredentials: true}});
    if (options!=null){
        Utils.prototype.objectPutAttrByObject(opt,options);
    }
    $.ajax(opt);
}

var Apis=function () {
    this.fileApis=new FileApis();
    this.userApis=new UserApis();
    this.passportApis=new PassportApis();
    this.productApis=new ProductApis();
    this.orderApis=new OrderApis();
}

var FileApis=function () {
    this.host=protocol_host.file;
}

FileApis.prototype.fileUpload=function (formData,success,error,options) {
    var opt={
        url:this.host+'/fileUpload',
        type:"POST",
        data:formData,
        async: true,
        cache: false,
        contentType: false,
        processData: false,
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

var UserApis=function () {
    this.host=protocol_host.user;
}

UserApis.prototype.getUsernameAndNickname=function (success,error,options) {
    var opt={
        url:this.host+'/getUsernameAndNickname',
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

var PassportApis=function () {
    this.host=protocol_host.passport;
}

PassportApis.prototype.userLogin=function (username,password,rememberMe,success,error,options) {
    var opt={
        url:this.host+'/userLogin',
        type:"POST",
        data:{
            username:username,
            password:password,
            rememberMe:rememberMe
        },
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

PassportApis.prototype.adminLogin=function (username,password,rememberMe,success,error,options) {
    var opt={
        url:this.host+'/adminLogin',
        type:"POST",
        data:{
            username:username,
            password:password,
            rememberMe:rememberMe
        },
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

var ProductApis=function () {
    this.host=protocol_host.product;
}

ProductApis.prototype.listBaseCatalog1s=function (success,error,options) {
    var opt={
        url:this.host+'/listBaseCatalog1s',
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.listBaseCatalog2sByCatalog1Id=function (catalog1Id,success,error,options) {
    var opt={
        url:this.host+'/listBaseCatalog2sByCatalog1Id/'+catalog1Id,
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.listBaseCatalog3sByCatalog2Id=function (catalog2Id,success,error,options) {
    var opt={
        url:this.host+'/listBaseCatalog3sByCatalog2Id/'+catalog2Id,
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.listBaseAttrInfosByCatalog3Id=function (catalog3Id,success,error,options) {
    var opt={
        url:this.host+'/listBaseAttrInfosByCatalog3Id/'+catalog3Id,
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.logicRemoveBaseAttrInfoById=function (id,success,error,options) {
    var opt={
        url:this.host+'/logicRemoveBaseAttrInfoById/'+id,
        type:"DELETE",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.listBaseAttrValuesByAttrId=function (attrId,success,error,options) {
    var opt={
        url:this.host+'/listBaseAttrValuesByAttrId/'+attrId,
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.logicRemoveBaseAttrValueById=function (id,success,error,options) {
    var opt={
        url:this.host+'/logicRemoveBaseAttrValueById/'+id,
        type:"DELETE",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.saveBaseAttrValue=function (jsonData,success,error,options) {
    var opt={
        url:this.host+'/saveBaseAttrValue',
        type:"PUT",
        data:jsonData,
        contentType: "application/json", //必须有
        dataType: "json", //表示返回值类型，不必须
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.saveBaseAttrInfo=function (jsonData,success,error,options) {
    var opt={
        url:this.host+'/saveBaseAttrInfo',
        type:"PUT",
        data:jsonData,
        contentType: "application/json", //必须有
        dataType: "json", //表示返回值类型，不必须
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.listProductInfosByCatalog3Id=function (catalog3Id,success,error,options) {
    var opt={
        url:this.host+'/listProductInfosByCatalog3Id/'+catalog3Id,
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.removeProductByProductId=function (productId,success,error,options) {
    var opt={
        url:this.host+'/removeProductByProductId/'+productId,
        type:"DELETE",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.listSkuInfosByProductId=function (productId,success,error,options) {
    var opt={
        url:this.host+'/listSkuInfosByProductId/'+productId,
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.removeSkuBySkuId=function (skuId,success,error,options) {
    var opt={
        url:this.host+'/removeSkuBySkuId/'+skuId,
        type:"DELETE",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.listBaseSaleAttrs=function (success,error,options) {
    var opt={
        url:this.host+'/listBaseSaleAttrs',
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.saveProduct=function (jsonData,success,error,options) {
    var opt={
        url:this.host+'/saveProduct',
        type:"PUT",
        data:jsonData,
        contentType: "application/json", //必须有
        dataType: "json", //表示返回值类型，不必须
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.listBaseAttrsByCatalog3Id=function (catalog3Id,success,error,options) {
    var opt={
        url:this.host+'/listBaseAttrsByCatalog3Id/'+catalog3Id,
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.listProductImagesByProductId=function (productId,success,error,options) {
    var opt={
        url:this.host+'/listProductImagesByProductId/'+productId,
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.listSaleAttrsByProductId=function (productId,success,error,options) {
    var opt={
        url:this.host+'/listSaleAttrsByProductId/'+productId,
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.saveSku=function (jsonData,success,error,options) {
    var opt={
        url:this.host+'/saveSku',
        type:"PUT",
        data:jsonData,
        contentType: "application/json", //必须有
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.listSkuInfosGroupByProductId=function (success,error,options) {
    var opt={
        url:this.host+'/listSkuInfosGroupByProductId',
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

ProductApis.prototype.listSkusByProductId=function (productId,success,error,options) {
    var opt={
        url:this.host+'/listSkusByProductId/'+productId,
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

var OrderApis=function () {
    this.host=protocol_host.order;
}

OrderApis.prototype.saveCartItem=function (skuId,quantity,success,error,options) {
    var opt={
        url:this.host+'/saveCartItem',
        type:"PUT",
        data:{
            skuId:skuId,
            quantity:quantity
        },
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

OrderApis.prototype.listCartItemsByUserId=function (success,error,options) {
    var opt={
        url:this.host+'/listCartItemsByUserId',
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

OrderApis.prototype.getCartItemById=function (cartItemId,success,error,options) {
    var opt={
        url:this.host+'/getCartItemById/'+cartItemId,
        type:"GET",
        success:success,
        error:error
    }
    baseAjax(opt,options);
}

// async	布尔值，表示请求是否异步处理。默认是 true。
// beforeSend(xhr)	发送请求前运行的函数。
// cache	布尔值，表示浏览器是否缓存被请求页面。默认是 true。
// complete(xhr,status)	请求完成时运行的函数（在请求成功或失败之后均调用，即在 success 和 error 函数之后）。
// contentType	发送数据到服务器时所使用的内容类型。默认是："application/x-www-form-urlencoded"。
// context	为所有 AJAX 相关的回调函数规定 "this" 值。
// data	规定要发送到服务器的数据。
// dataFilter(data,type)	用于处理 XMLHttpRequest 原始响应数据的函数。
// dataType	预期的服务器响应的数据类型。
// error(xhr,status,error)	如果请求失败要运行的函数。
// global	布尔值，规定是否为请求触发全局 AJAX 事件处理程序。默认是 true。
// ifModified	布尔值，规定是否仅在最后一次请求以来响应发生改变时才请求成功。默认是 false。
// jsonp	在一个 jsonp 中重写回调函数的字符串。
// jsonpCallback	在一个 jsonp 中规定回调函数的名称。
// password	规定在 HTTP 访问认证请求中使用的密码。
// processData	布尔值，规定通过请求发送的数据是否转换为查询字符串。默认是 true。
// scriptCharset	规定请求的字符集。
// success(result,status,xhr)	当请求成功时运行的函数。
// timeout	设置本地的请求超时时间（以毫秒计）。
// traditional	布尔值，规定是否使用参数序列化的传统样式。
// type	规定请求的类型（GET 或 POST）。
// url	规定发送请求的 URL。默认是当前页面。
// username	规定在 HTTP 访问认证请求中使用的用户名。
// xhr	用于创建 XMLHttpRequest 对象的函数。