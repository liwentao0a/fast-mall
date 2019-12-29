var apis = new Apis();
var utils = new Utils();

$(function () {

    var usernameCookie = utils.getCookie("username");
    if (usernameCookie!=null){
        $('#manageLoginForm input[name="username"]').val(usernameCookie);
    }

    $('#manageLoginForm').on('submit',function (e) {
        e.preventDefault();
        var username = $(this).find('input[name="username"]').val();
        var password = $(this).find('input[name="password"]').val();
        var rememberMe = $(this).find('input[name="rememberMe"]').val();
        apis.passportApis.adminLogin(username,password,rememberMe,function (result) {
            if (result.code==code.SUCCESS){
                var callbackUrl = utils.getUrlParam('callbackUrl');
                if (callbackUrl!=null){
                    if (callbackUrl.indexOf("?")==-1){
                        window.location.href=callbackUrl+"?user-token="+result.data;
                    } else {
                        window.location.href=callbackUrl+"&user-token="+result.data;
                    }
                } else {
                    window.location.href=protocol_host.page+"/admin/manage.do?user-token="+result.data;
                }
            } else if (result.code==code.PERMISSION_NO_ACCESS){
                $('#tipsModal .modal-body').html('权限不足');
                $('#tipsModal').modal('show');
            } else {
                $('#tipsModal .modal-body').html('用户名密码错误');
                $('#tipsModal').modal('show');
            }
        },null,{async:false});
    });
});