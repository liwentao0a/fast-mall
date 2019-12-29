var utils = new Utils();
var apis = new Apis();

$(function () {
    var urlParamCartItemId = utils.getUrlParam('cartItemId');
    if (urlParamCartItemId!=null){
        apis.orderApis.getCartItemById(urlParamCartItemId,function (result) {
            if (result.code==code.SUCCESS){
                var data = result.data;
                $('#carAddSuccess .carAddProductThumbnail').attr('src',data.productPic);
                $('#carAddSuccess .productName').html(data.productName);
                var productAttrJson = JSON.parse(data.productAttr);
                var carAddProductDescSaleAttrMapHtml='';
                $.each(productAttrJson,function (i,item) {
                    carAddProductDescSaleAttrMapHtml+='<span>'+item.key+':'+item.value+'</span>';
                });
                carAddProductDescSaleAttrMapHtml+='<span>/ 数量：'+data.quantity+'</span>';
                $('#carAddSuccess .carAddProductDescSaleAttrMap').html(carAddProductDescSaleAttrMapHtml);
                $('#carAddBtnBody .goItemBtn').attr('productId',data.productId);
                //todo
            }
        },null,null);
    }

    $('#carAddBtnBody .goItemBtn').on('click',function () {
        if (utils.existAttr($(this),'productId')) {
            var productId = $(this).attr('productId');
            window.location.href=protocol_host.page+'/item.do?productId='+productId;
        }
    });

    $('#carAddBtnBody .goCarBtn').on('click',function () {
        window.location.href=protocol_host.page+'/user/car.do';
    });
});