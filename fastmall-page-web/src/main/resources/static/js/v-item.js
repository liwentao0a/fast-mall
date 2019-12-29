var apis = new Apis();
var utils = new Utils();

var listSkusByProductIdData = null;
var saleAttrIdRuler = new Array();
var saleAttrIdRuler_spuId = new Map([]);

$(function () {

    initTitleBar();
    initProductItem();

    function initTitleBar() {
        apis.userApis.getUsernameAndNickname(function (result) {
            if (result.code == code.SUCCESS) {
                console.log(result.data)
                $('#tbLogin .login').html(result.data.nickname);
            }
        }, null, null);
    }


    function initProductItem() {
        $('#productItemImageBody .productItemThumbnailList').on('click', '.productItemThumbnailBox', function () {
            var imgSrc = $(this).find('.productItemThumbnail').attr('src');
            $('#productItemImageBody .productItemImage').attr('src', imgSrc);
        });

        $('#quantity .sub').on('click', function () {
            var quantity = $('#quantity .text_box').val();
            if (utils.isIntegerNumber(quantity)) {
                quantity--;
            }
            if (quantity < 1) {
                quantity = 1;
            }
            if (quantity > 200) {
                quantity = 200;
            }
            $('#quantity .text_box').val(quantity);
        });

        $('#quantity .add').on('click', function () {
            var quantity = $('#quantity .text_box').val();
            if (utils.isIntegerNumber(quantity)) {
                quantity++;
            }
            if (quantity < 1) {
                quantity = 1;
            }
            if (quantity > 200) {
                quantity = 200;
            }
            $('#quantity .text_box').val(quantity);
        });

        var urlParamProductId = utils.getUrlParam('productId');
        if (urlParamProductId != null) {
            apis.productApis.listSkusByProductId(urlParamProductId, function (result) {
                if (result.code == code.SUCCESS) {
                    var data = result.data;
                    listSkusByProductIdData = data;
                    $.each(data, function (i, item) {
                        if (i == 0) {
                            apis.productApis.listSaleAttrsByProductId(urlParamProductId, function (result) {
                                var html = '';
                                if (result.code == code.SUCCESS) {
                                    $.each(result.data, function (i, item) {
                                        var productSaleAttrValuesHtml = '';
                                        $.each(item.productSaleAttrValues, function (j, itemj) {
                                            productSaleAttrValuesHtml += '<dd sale-attr-value-id="' + itemj.id + '">' + itemj.saleAttrValueName + '</dd>';
                                        });
                                        html += '<ol class="saleAttr clear" sale-attr-id="' + item.saleAttrId + '">\n' +
                                            '      <dt>' + item.saleAttrName + '</dt>\n' + productSaleAttrValuesHtml +
                                            '  </ol>';
                                        saleAttrIdRuler.push(item.saleAttrId);
                                    });
                                }
                                $('#chooseSaleAttrs').html(html);
                            }, null, {async: false});


                            $.each(item.skuSaleAttrValues, function (j, itemj) {
                                $('#chooseSaleAttrs').children('ol[sale-attr-id="' + itemj.saleAttrId + '"]')
                                    .children('dd[sale-attr-value-id="' + itemj.saleAttrValueId + '"]')
                                    .addClass('ddSelected')
                                    .siblings()
                                    .removeClass('ddSelected');
                            });

                            initProductItemSku(item);
                        }

                        var key=new Array();
                        $.each(saleAttrIdRuler,function (j, itemj) {
                            $.each(item.skuSaleAttrValues,function (k, itemk) {
                                if (itemk.saleAttrId==itemj){
                                    key.push(itemk.saleAttrValueId);
                                }
                            });
                        });
                        if (key.length>0){
                            saleAttrIdRuler_spuId.set(key.toString(),item.id);
                        }
                    });
                    initSaleAttrValueIsOptional();
                }
                console.log(saleAttrIdRuler_spuId)
            }, null, null);

            function initProductItemSku(sku) {
                var $productItemDetailsBody = $('#productItemDetailsBody');
                $productItemDetailsBody.attr("skuId",sku.id);
                $productItemDetailsBody.find('.skuName').html(sku.skuName);
                $productItemDetailsBody.find('.skuDesc').html(sku.skuDesc);
                $productItemDetailsBody.find('.price').html(sku.price);
                $productItemDetailsBody.find('.weight').html(sku.weight);
                $('#productItemImageBody .productItemImage').attr('src', sku.skuDefaultImg);

                var productItemThumbnailListHtml = '';
                $.each(sku.skuImages, function (j, itemj) {
                    productItemThumbnailListHtml += '<div class="productItemThumbnailBox">\n' +
                        '                <img class="productItemThumbnail" src="' + itemj.imgUrl + '"/>\n' +
                        '            </div>';
                });
                $('#productItemImageBody .productItemThumbnailList').html(productItemThumbnailListHtml);
            }

            $('#chooseSaleAttrs').on('click', '.saleAttr>dd', function () {
                if($(this).hasClass('ddDisable')){
                    return false;
                }
                $(this).addClass('ddSelected')
                    .siblings()
                    .removeClass('ddSelected');
                initSaleAttrValueIsOptional();
                var key='';
                var isSelectionDone=true;
                $('#chooseSaleAttrs .saleAttr').each(function (i) {
                    if ($(this).children().hasClass('ddSelected')){
                        var saleAttrValueId=$(this).children('.ddSelected').attr('sale-attr-value-id');
                         if (i==0){
                             key=saleAttrValueId;
                         } else {
                             key+=','+saleAttrValueId;
                         }
                    }else {
                        isSelectionDone=false;
                    }
                });
                if (isSelectionDone){
                    var spuId = saleAttrIdRuler_spuId.get(key);
                    console.log('isSelectionDone'+spuId);
                    $.each(listSkusByProductIdData,function (i, item) {
                        if (item.id==spuId){
                            initProductItemSku(item);
                        }
                    });
                }
            });

            function initSaleAttrValueIsOptional() {
                var keyPre='';
                $('#chooseSaleAttrs .saleAttr').each(function (i) {
                    $(this).children('dd').addClass('ddDisable');
                    var saleAttrValueId = $(this).find('.ddSelected').attr('sale-attr-value-id');
                    $(this).find('dd').each(function () {
                        var $dd = $(this);
                        var tempSaleAttrValueId = $dd.attr('sale-attr-value-id');
                        var tempKey=keyPre+','+tempSaleAttrValueId;
                        if (i==0){
                            tempKey=tempSaleAttrValueId;
                        }
                        console.log(tempKey)
                        var isOptional=false;
                        saleAttrIdRuler_spuId.forEach(function (v, k) {
                            if (k.indexOf(tempKey)==0){
                                isOptional=true;
                            }
                        });
                        if (isOptional){
                            $dd.removeClass('ddDisable');
                        } else {
                            if ($dd.hasClass('ddSelected')) {
                                $dd.removeClass('ddSelected');
                                keyPre='removeDdSelected';
                            }
                        }
                    });

                    if (i==0){
                        keyPre+=saleAttrValueId;
                    } else {
                        keyPre+=','+saleAttrValueId;
                    }
                });
            }

        }

        $('#addCarBtn').on('click',function () {
            var quantity = $('#quantity input[name="quantity"]').val();
            var skuId = $('#productItemDetailsBody').attr("skuId");
            if (!utils.isIntegerNumber(quantity)){
                quantity=1;
            }
            apis.orderApis.saveCartItem(skuId,quantity,function (result) {
                if(result.code==code.SUCCESS){
                    window.location.href=protocol_host.page+'/car-add-success.do?cartItemId='+result.data;
                }else {
                    $('#tipsModal .modal-body').html('添加失败');
                    $('#tipsModal').modal('show');
                }
            },null,null);
        });

    }


});