var Utils=function(){
}

/**
 *  html片段生成
 * var toHtml1 = compile({
 *    title:{a:1},
 *    content:2
 * },function () {/*
 * <div>
 *     <h2>${title.a}</h2>
 *     <div class="content">${content}</div>
 * </div>
 *
 * });
 * @param data
 * @param htmlFunctionObject
 * @returns {string}
 */
Utils.prototype.toHtml=function (data,htmlFunctionObject) {
    return htmlFunctionObject.toString().match(/\/\*([\s\S]*?)\*\//)[1].replace(/\$\{\w.+\}/g, function (variable) {
        var value = data;
        variable = variable.replace('${', '').replace('}', '');
        variable.split('.').forEach(function (section) {
            value = value[section];
        });
        return value;
    });
}

/**
 * 获取文件上传组件的文件路径
 * @param fileInputJqObject
 * @returns {*}
 */
Utils.prototype.getFileInputDataUrl=function (fileInputJqObject) {
    var $file = fileInputJqObject;
    var fileObj = $file[0];
    var windowURL = window.URL || window.webkitURL;
    var dataURL=null;
    if (fileObj && fileObj.files && fileObj.files[0]) {
        dataURL = windowURL.createObjectURL(fileObj.files[0]);
    }
    return dataURL;
}

/**
 * 判断是否包含某个属性
 * @param jqObject
 * @returns {boolean}
 */
Utils.prototype.existAttr=function existAttr(jqObject,attrName) {
    var attr = jqObject.attr(attrName);
    if (typeof attr !== typeof undefined && attr !== false) {
        return true;
    }
    return false;
}

/**
 * 判断是否是正整数
 * @param str
 * @returns {boolean}
 */
Utils.prototype.isPositiveIntegerNumber=function isPositiveIntegerNumber(str) {
    var regExp=/^[0-9]*[1-9][0-9]*$/;
    if (regExp.test(str)){
        return true;
    }
    return false;
}

/**
 * 判断是否是非负整数
 * @param str
 * @returns {boolean}
 */
Utils.prototype.isNonNegativeIntegerNumber=function isNonNegativeIntegerNumber(str) {
    var regExp=/^\d+$/;
    if (regExp.test(str)){
        return true;
    }
    return false;
}

/**
 * 判断是否是整数
 * @param str
 * @returns {boolean}
 */
Utils.prototype.isIntegerNumber=function isIntegerNumber(str) {
    var regExp=/^-?\d+$/;
    if (regExp.test(str)){
        return true;
    }
    return false;
}

/**
 * 为object添加另一个object的属性，覆盖存在的属性
 * @param object
 * @param by
 */
Utils.prototype.objectPutAttrByObject=function objectPutAttrByObject(object,by) {
    var keys = Object.keys(by);
    for(var i=0;i<keys.length;i++){
        var key = keys[i];
        object[key]=by[key];
    }
}

/**
 * 获取url参数
 * @param name
 * @returns {string|null}
 */
Utils.prototype.getUrlParam=function (name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)'); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

/**
 * 获取cookie
 * @param nameOfCookie
 * @returns {string|null}
 */
Utils.prototype.getCookie=function (nameOfCookie) {
    if (document.cookie.length > 0) {
        var begin = document.cookie.indexOf(nameOfCookie + "=");
        if (begin !== -1) {
            begin += nameOfCookie.length + 1;
            var end = document.cookie.indexOf(";", begin);
            if (end === -1) end = document.cookie.length;
            return unescape(document.cookie.substring(begin, end));
        }
    }
    return null;
}

/**
 * 从cookie获取，获取不到再从url请求参数获取
 * @param name
 * @returns {*}
 */
Utils.prototype.getCookieOrUrlParam=function (name) {
    var value = getCookie(name);
    if (value==null){
        value=getUrlParam(name);
    }
    return value;
}