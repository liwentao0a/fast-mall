/*
全局变量区
 */
var apis = new Apis();
var utils = new Utils();
var listBaseCatalog1sData=null;
var listBaseCatalog2sByCatalog1IdData={};
var listBaseCatalog3sByCatalog2IdData={};
var listBaseSaleAttrsData=null;
var $fileInput = $('<input type="file" accept="image/*">');



//面包屑
function htmlForBreadcrumb(breadcrumbs) {
    var html='';
    for (var i=0;i<breadcrumbs.length;i++){
        if (i==breadcrumbs.length-1){
            html+='<li class="breadcrumb-item active" aria-current="page">'+breadcrumbs[i]+'</li>';
        } else {
            html+='<li class="breadcrumb-item"><span style="color: #007bff;">'+breadcrumbs[i]+'</span></li>';
        }
    }
    $('#titleBar #breadcrumb .breadcrumb').html(html);
}
//切换管理界面
function switchManageBody(fragment){
    $('#manageBody>*').hide();
    $(fragment).show();
}



$(function () {

    initSideBar();
    initBaseAttrListBody();
    initSaveBaseAttrInfoBody();
    initSpuManageBody();
    initSaveSpuBody();
    $('#sideBar .item-header').eq(0).click();
    $('#sideBar .item-body').eq(0).children().eq(0).click();

    //初始化侧边栏
    function initSideBar() {
        $('#sideBar .item-body>*').on('click',function () {
            $('#sideBar .item-body>*').removeClass('sideBarItemBodyItemClick');
            $(this).addClass('sideBarItemBodyItemClick');
            var fragment = $(this).attr('fragment');
            switchManageBody(fragment);
            switch (fragment) {
                case '#baseAttrListBody':
                    htmlForBreadcrumb(['基本信息管理','平台属性列表']);
                    break;
                case '#saveBaseAttrInfoBody':
                    htmlForBreadcrumb(['基本信息管理','添加平台属性']);
                    break;
                case '#spuManageBody':
                    htmlForBreadcrumb(['商品信息管理','商品属性SPU管理']);
                    break;
                case '#saveSpuBody':
                    htmlForBreadcrumb(['商品信息管理','添加SPU']);
                    break;
            }
        });
    }

    //初始化平台属性列表
    function initBaseAttrListBody() {
        initCatalog1();

        //初始化一级分类
        function initCatalog1() {
            if (listBaseCatalog1sData==undefined||listBaseCatalog2sByCatalog1IdData==null){
                apis.productApis.listBaseCatalog1s(function (result) {
                    if (result.code==code.SUCCESS){
                        listBaseCatalog1sData=result.data;
                    }
                    htmlForBaseAttrListBodyCatalog1();
                },null,null);
            } else {
                htmlForBaseAttrListBodyCatalog1();
            }
        }

        $('#baseAttrListBody .catalog1 select[name="catalog1Id"]').on('change',function () {
            var catalog1Id = $(this).val();
            if (catalog1Id=='请选择') {
                return false;
            }
            $('#baseAttrListBody .catalog3 select[name="catalog3Id"]').val("请选择");
            var listBaseCatalog2sByCatalog1Id = listBaseCatalog2sByCatalog1IdData[catalog1Id];
            if (listBaseCatalog2sByCatalog1Id==undefined||listBaseCatalog2sByCatalog1Id==null){
                apis.productApis.listBaseCatalog2sByCatalog1Id(catalog1Id,function (result) {
                    if (result.code==code.SUCCESS){
                        listBaseCatalog2sByCatalog1IdData[catalog1Id]=result.data;
                    }
                    htmlForBaseAttrListBodyCatalog2(catalog1Id);
                },null,null);
            } else {
                htmlForBaseAttrListBodyCatalog2(catalog1Id);
            }
        });
        $('#baseAttrListBody .catalog2 select[name="catalog2Id"]').on('change',function () {
            var catalog2Id = $(this).val();
            if (catalog2Id=='请选择') {
                return false;
            }
            var listBaseCatalog3sByCatalog2Id = listBaseCatalog3sByCatalog2IdData[catalog2Id];
            if (listBaseCatalog3sByCatalog2Id==undefined||listBaseCatalog3sByCatalog2Id==null){
                apis.productApis.listBaseCatalog3sByCatalog2Id(catalog2Id,function (result) {
                    if (result.code==code.SUCCESS){
                        listBaseCatalog3sByCatalog2IdData[catalog2Id]=result.data;
                    }
                    htmlForBaseAttrListBodyCatalog3(catalog2Id);
                },null,null);
            } else {
                htmlForBaseAttrListBodyCatalog3(catalog2Id);
            }
        });
        $('#baseAttrListBody .catalog3 select[name="catalog3Id"]').on('change',function () {
            var catalog3Id = $(this).val();
            htmlForBaseAttrListBodyBaseAttrList(catalog3Id);
        });
        $('#baseAttrListBody .baseAttrList').on('click','.editBaseAttrValueBtn',function () {
            var fragment = $(this).attr('fragment');
            var baseAttrId = $(this).attr('base-attr-id');
            var baseAttrName = $(this).attr('base-attr-name');
            switchManageBody(fragment);
            htmlForBreadcrumb(['基本信息管理','平台属性列表','编辑平台属性值']);
            initEditBaseAttrValueBody(baseAttrId,baseAttrName);
        });
        $('#baseAttrListBody .baseAttrList').on('click','.removeBaseAttrBtn',function () {
            var baseAttrId = $(this).attr('base-attr-id');
            apis.productApis.logicRemoveBaseAttrInfoById(baseAttrId,function (result) {
                if (result.code==code.SUCCESS){
                    $('#tipsModal .modal-body').html('删除成功');
                    $('#tipsModal').modal('show');
                    var catalog3Id = $('#baseAttrListBody .catalog3 select[name="catalog3Id"]').val();
                    htmlForBaseAttrListBodyBaseAttrList(catalog3Id);
                }else {
                    $('#tipsModal .modal-body').html('删除失败');
                    $('#tipsModal').modal('show');
                }
            },null,null);
        });

        //初始化编辑平台属性值
        function initEditBaseAttrValueBody(baseAttrId,baseAttrName) {
            $('#editBaseAttrValueBody .baseAttrName').html(baseAttrId+"-"+baseAttrName);
            $('#editBaseAttrValueBody').attr('base-attr-id',baseAttrId)
                .attr('base-attr-name',baseAttrName);
            htmlForEditBaseAttrValueBodyBaseAttrValueList(baseAttrId);

            $('#saveBaseAttrValueModal .saveBaseAttrValueBtn').on('click',function () {
                $('#saveBaseAttrValueModal').modal('hide');
                var baseAttrValueName = $('#saveBaseAttrValueModal input[name="baseAttrValueName"]').val();
                if (baseAttrValueName==''){
                    $('#tipsModal .modal-body').html('平台属性值不能为空');
                    $('#tipsModal').modal('show');
                    return false;
                }
                apis.productApis.saveBaseAttrValue(JSON.stringify({
                    valueName:baseAttrValueName,
                    attrId:baseAttrId
                }),function (result) {
                    if (result.code==code.SUCCESS){
                        $('#tipsModal .modal-body').html('平台属性值添加成功');
                        $('#tipsModal').modal('show');
                        htmlForEditBaseAttrValueBodyBaseAttrValueList(baseAttrId);
                    }else {
                        $('#tipsModal .modal-body').html('平台属性值添加失败');
                        $('#tipsModal').modal('show');
                    }
                },null,null);
            });

            $('#editBaseAttrValueBody .baseAttrValueList').on('click','.removeBaseAttrValueBtn',function () {
                var baseAttrValueId = $(this).attr('base-attr-value-id');
                apis.productApis.logicRemoveBaseAttrValueById(baseAttrValueId,function (result) {
                    if (result.code==code.SUCCESS){
                        htmlForEditBaseAttrValueBodyBaseAttrValueList(baseAttrId);
                    }else {
                        $('#tipsModal .modal-body').html('平台属性值删除失败');
                        $('#tipsModal').modal('show');
                    }
                },null,null);
            });
        }

        //html修改方法
        function htmlForEditBaseAttrValueBodyBaseAttrValueList(baseAttrId) {
            apis.productApis.listBaseAttrValuesByAttrId(baseAttrId,function (result) {
                var html='';
                if (result.code==code.SUCCESS){
                    $.each(result.data,function (i, item) {
                        html+='<tr>\n' +
                            '      <th>'+(i+1)+'</th>\n' +
                            '      <td>'+item.id+'</td>\n' +
                            '      <td>'+item.valueName+'</td>\n' +
                            '      <td>\n' +
                            '          <button type="button" class="btn btn-danger removeBaseAttrValueBtn" base-attr-value-id="'+item.id+'">删除</button>\n' +
                            '      </td>\n' +
                            '  </tr>';
                    });
                }
                $('#editBaseAttrValueBody .baseAttrValueList tbody').html(html);
            },null,null);
        }

        function htmlForBaseAttrListBodyBaseAttrList(catalog3Id) {
            if (catalog3Id=='请选择') {
                $('#baseAttrListBody .baseAttrList tbody').html('');
                return false;
            }
            apis.productApis.listBaseAttrInfosByCatalog3Id(catalog3Id,function (result) {
                var html='';
                if (result.code==code.SUCCESS){
                    $.each(result.data,function (i, item) {
                        html+='<tr>\n' +
                            '      <th>'+(i+1)+'</th>\n' +
                            '      <td>'+item.id+'</td>\n' +
                            '      <td>'+item.attrName+'</td>\n' +
                            '      <td>\n' +
                            '          <button type="button" class="btn btn-primary editBaseAttrValueBtn" fragment="#editBaseAttrValueBody" base-attr-id="'+item.id+'" base-attr-name="'+item.attrName+'">编辑平台属性值</button>\n' +
                            '          <button type="button" class="btn btn-danger removeBaseAttrBtn" base-attr-id="'+item.id+'">删除</button>\n' +
                            '      </td>\n' +
                            '  </tr>';
                    });
                }
                $('#baseAttrListBody .baseAttrList tbody').html(html);
            },null,null);
        }

        function htmlForBaseAttrListBodyCatalog3(catalog2Id) {
            var listBaseCatalog3sByCatalog2Id = listBaseCatalog3sByCatalog2IdData[catalog2Id];
            var html='<option>请选择</option>';
            if (listBaseCatalog3sByCatalog2Id!=undefined&&listBaseCatalog3sByCatalog2Id!=null){
                $.each(listBaseCatalog3sByCatalog2Id,function (i, item) {
                    html += '<option value="' + item.id + '">' + item.name + '</option>';
                });
            }
            $('#baseAttrListBody .catalog3 select[name="catalog3Id"]').html(html);
        }

        function htmlForBaseAttrListBodyCatalog2(catalog1Id) {
            var listBaseCatalog2sByCatalog1Id = listBaseCatalog2sByCatalog1IdData[catalog1Id];
            var html='<option>请选择</option>';
            if (listBaseCatalog2sByCatalog1Id!=undefined&&listBaseCatalog2sByCatalog1Id!=null){
                $.each(listBaseCatalog2sByCatalog1Id,function (i, item) {
                    html += '<option value="' + item.id + '">' + item.name + '</option>';
                });
            }
            $('#baseAttrListBody .catalog2 select[name="catalog2Id"]').html(html);
        }

        function htmlForBaseAttrListBodyCatalog1() {
            var html='<option>请选择</option>';
            if (listBaseCatalog1sData!=undefined&&listBaseCatalog1sData!=null){
                $.each(listBaseCatalog1sData,function (i, item) {
                    html+='<option value="' + item.id + '">' + item.name + '</option>';
                });
            }
            $('#baseAttrListBody .catalog1 select[name="catalog1Id"]').html(html);
        }
    }

    //初始化添加平台属性
    function initSaveBaseAttrInfoBody() {
        initCatalog1();

        //初始化一级分类
        function initCatalog1() {
            if (listBaseCatalog1sData==undefined||listBaseCatalog2sByCatalog1IdData==null){
                apis.productApis.listBaseCatalog1s(function (result) {
                    if (result.code==code.SUCCESS){
                        listBaseCatalog1sData=result.data;
                    }
                    htmlForSaveBaseAttrInfoBodyCatalog1();
                },null,null);
            } else {
                htmlForSaveBaseAttrInfoBodyCatalog1();
            }
        }

        //事件绑定
        $('#saveBaseAttrInfoBody .catalog1 select[name="catalog1Id"]').on('change',function () {
            var catalog1Id = $(this).val();
            if (catalog1Id=='请选择') {
                return false;
            }
            $('#saveBaseAttrInfoBody .catalog3 select[name="catalog3Id"]').val("请选择");
            var listBaseCatalog2sByCatalog1Id = listBaseCatalog2sByCatalog1IdData[catalog1Id];
            if (listBaseCatalog2sByCatalog1Id==undefined||listBaseCatalog2sByCatalog1Id==null){
                apis.productApis.listBaseCatalog2sByCatalog1Id(catalog1Id,function (result) {
                    if (result.code==code.SUCCESS){
                        listBaseCatalog2sByCatalog1IdData[catalog1Id]=result.data;
                    }
                    htmlForSaveBaseAttrInfoBodyCatalog2(catalog1Id);
                },null,null);
            } else {
                htmlForSaveBaseAttrInfoBodyCatalog2(catalog1Id);
            }
        });
        $('#saveBaseAttrInfoBody .catalog2 select[name="catalog2Id"]').on('change',function () {
            var catalog2Id = $(this).val();
            if (catalog2Id=='请选择') {
                return false;
            }
            var listBaseCatalog3sByCatalog2Id = listBaseCatalog3sByCatalog2IdData[catalog2Id];
            if (listBaseCatalog3sByCatalog2Id==undefined||listBaseCatalog3sByCatalog2Id==null){
                apis.productApis.listBaseCatalog3sByCatalog2Id(catalog2Id,function (result) {
                    if (result.code==code.SUCCESS){
                        listBaseCatalog3sByCatalog2IdData[catalog2Id]=result.data;
                    }
                    htmlForSaveBaseAttrInfoBodyCatalog3(catalog2Id);
                },null,null);
            } else {
                htmlForSaveBaseAttrInfoBodyCatalog3(catalog2Id);
            }
        });
        $('#saveBaseAttrInfoBody .saveBaseAttrBtn').on('click',function () {
            var catalog3Id = $('#saveBaseAttrInfoBody .catalog3 select[name="catalog3Id"]').val();
            var attrName = $('#saveBaseAttrInfoBody input[name="attrName"]').val();
            if (attrName==''){
                $('#tipsModal .modal-body').html('平台属性名不能为空');
                $('#tipsModal').modal('show');
                return false;
            }
            apis.productApis.saveBaseAttrInfo(JSON.stringify({
                attrName:attrName,
                catalog3Id:catalog3Id
            }),function (result) {
                if (result.code==code.SUCCESS){
                    $('#tipsModal .modal-body').html('平台属性添加成功');
                    $('#tipsModal').modal('show');
                    $('#baseAttrListBody select[name="catalog3Id"]').change();
                }else{
                    $('#tipsModal .modal-body').html('平台属性添加失败');
                    $('#tipsModal').modal('show');
                }
            },null,null);
        });

        //html修改方法
        function htmlForSaveBaseAttrInfoBodyCatalog3(catalog2Id) {
            var listBaseCatalog3sByCatalog2Id = listBaseCatalog3sByCatalog2IdData[catalog2Id];
            var html='<option>请选择</option>';
            if (listBaseCatalog3sByCatalog2Id!=undefined&&listBaseCatalog3sByCatalog2Id!=null){
                $.each(listBaseCatalog3sByCatalog2Id,function (i, item) {
                    html += '<option value="' + item.id + '">' + item.name + '</option>';
                });
            }
            $('#saveBaseAttrInfoBody .catalog3 select[name="catalog3Id"]').html(html);
        }

        function htmlForSaveBaseAttrInfoBodyCatalog2(catalog1Id) {
            var listBaseCatalog2sByCatalog1Id = listBaseCatalog2sByCatalog1IdData[catalog1Id];
            var html='<option>请选择</option>';
            if (listBaseCatalog2sByCatalog1Id!=undefined&&listBaseCatalog2sByCatalog1Id!=null){
                $.each(listBaseCatalog2sByCatalog1Id,function (i, item) {
                    html += '<option value="' + item.id + '">' + item.name + '</option>';
                });
            }
            $('#saveBaseAttrInfoBody .catalog2 select[name="catalog2Id"]').html(html);
        }

        function htmlForSaveBaseAttrInfoBodyCatalog1() {
            var html='<option>请选择</option>';
            if (listBaseCatalog1sData!=undefined&&listBaseCatalog1sData!=null){
                $.each(listBaseCatalog1sData,function (i, item) {
                    html+='<option value="' + item.id + '">' + item.name + '</option>';
                });
            }
            $('#saveBaseAttrInfoBody .catalog1 select[name="catalog1Id"]').html(html);
        }
    }

    //初始化商品属性SPU管理
    function initSpuManageBody() {
        initCatalog1();

        //初始化一级分类
        function initCatalog1() {
            if (listBaseCatalog1sData==undefined||listBaseCatalog2sByCatalog1IdData==null){
                apis.productApis.listBaseCatalog1s(function (result) {
                    if (result.code==code.SUCCESS){
                        listBaseCatalog1sData=result.data;
                    }
                    htmlForSpuManageBodyCatalog1();
                },null,null);
            } else {
                htmlForSpuManageBodyCatalog1();
            }
        }

        //事件绑定
        $('#spuManageBody .catalog1 select[name="catalog1Id"]').on('change',function () {
            var catalog1Id = $(this).val();
            if (catalog1Id=='请选择') {
                return false;
            }
            $('#spuManageBody .catalog3 select[name="catalog3Id"]').val("请选择");
            var listBaseCatalog2sByCatalog1Id = listBaseCatalog2sByCatalog1IdData[catalog1Id];
            if (listBaseCatalog2sByCatalog1Id==undefined||listBaseCatalog2sByCatalog1Id==null){
                apis.productApis.listBaseCatalog2sByCatalog1Id(catalog1Id,function (result) {
                    if (result.code==code.SUCCESS){
                        listBaseCatalog2sByCatalog1IdData[catalog1Id]=result.data;
                    }
                    htmlForSpuManageBodyCatalog2(catalog1Id);
                },null,null);
            } else {
                htmlForSpuManageBodyCatalog2(catalog1Id);
            }
        });
        $('#spuManageBody .catalog2 select[name="catalog2Id"]').on('change',function () {
            var catalog2Id = $(this).val();
            if (catalog2Id=='请选择') {
                return false;
            }
            var listBaseCatalog3sByCatalog2Id = listBaseCatalog3sByCatalog2IdData[catalog2Id];
            if (listBaseCatalog3sByCatalog2Id==undefined||listBaseCatalog3sByCatalog2Id==null){
                apis.productApis.listBaseCatalog3sByCatalog2Id(catalog2Id,function (result) {
                    if (result.code==code.SUCCESS){
                        listBaseCatalog3sByCatalog2IdData[catalog2Id]=result.data;
                    }
                    htmlForSpuManageBodyCatalog3(catalog2Id);
                },null,null);
            } else {
                htmlForSpuManageBodyCatalog3(catalog2Id);
            }
        });
        $('#spuManageBody .catalog3 select[name="catalog3Id"]').on('change',function () {
            var catalog3Id = $(this).val();
            if (catalog3Id=='请选择') {
                return false;
            }
            htmlForSpuManageBodySpuList(catalog3Id);
        });
        $('#spuManageBody .spuList').on('click','.removeSpuBtn',function () {
            var spuId = $(this).attr('spu-id');
            apis.productApis.removeProductByProductId(spuId,function (result) {
                if (result.code == code.SUCCESS) {
                    $('#spuManageBody .catalog3 select[name="catalog3Id"]').change();
                }else {
                    $('#tipsModal .modal-body').html('删除失败');
                    $('#tipsModal').modal('show');
                }
            },null,null);
        });
        $('#spuManageBody .spuList').on('click','.editSkuBtn',function () {
            var spuId = $(this).attr('spu-id');
            var spuName = $(this).attr('spu-name');
            var fragment = $(this).attr('fragment');
            switchManageBody(fragment);
            htmlForBreadcrumb(['商品信息管理','商品属性SPU管理','编辑SKU']);

            var catalog3Id = $('#spuManageBody .catalog3 select[name="catalog3Id"]').val();
            initEditSkuBody(spuId,spuName,catalog3Id);
        });

        //初始化编辑SKU
        function initEditSkuBody(spuId,spuName,catalog3Id) {
            $('#editSkuBody .spuId-spuName').html(spuId+'-'+spuName);

            htmlForEditSkuBodySkuList(spuId);

            $('#editSkuBody .skuList').on('click','.removeSkuBtn',function () {
                var skuId = $(this).attr('sku-id');
                apis.productApis.removeSkuBySkuId(skuId,function (result) {
                    if (result.code==code.SUCCESS){
                        htmlForEditSkuBodySkuList(spuId);
                    } else{
                        $('#tipsModal .modal-body').html('删除失败');
                        $('#tipsModal').modal('show');
                    }
                },null,null);
            });

            htmlForSaveSkuBodyImgCards(spuId);

            htmlForSaveSkuBodySaleAttrs(spuId);

            htmlForSaveSkuBodyBaseAttrs(catalog3Id);

            $('#saveSkuBody .imgCards').on('click','.card-img-top-box',function () {
                $(this).parent().toggleClass('cardSelected');
            });

            $('#saveSkuModal .saveSkuBtn').on('click',function () {
                var skuName = $('#saveSkuBody input[name="skuName"]').val();
                var skuDesc = $('#saveSkuBody textarea[name="skuDesc"]').val();
                var price = $('#saveSkuBody input[name="price"]').val();
                var weight = $('#saveSkuBody input[name="weight"]').val();

                var skuDefaultImgId = $('#saveSkuBody .imgCards input[name="skuDefaultImgId"]:checked').val();
                var skuDefaultImg='';
                var skuImages = new Array();
                $('#saveSkuBody .imgCards .cardSelected').each(function () {
                    var productImgId = $(this).attr('image-id');
                    var isDefault="0";
                    alert(skuDefaultImgId)
                    if (productImgId==skuDefaultImgId){
                        alert(productImgId)
                        isDefault="1";
                        skuDefaultImg=$(this).attr('img-url');
                    }
                    var skuImage={
                        productImgId:productImgId,
                        isDefault:isDefault
                    }
                    skuImages.push(skuImage);
                });
                if (skuDefaultImg==''){
                    skuDefaultImg=$('#saveSkuBody .imgCards .cardSelected').first().attr('img-url');
                }
                var skuSaleAttrValues = new Array();
                $('#saveSkuBody .saleAttrs select[name="saleAttr"]').each(function () {
                    var val = $(this).val();
                    if (val!='请选择'){
                        var saleAttrId = $(this).attr('sale-attr-id');
                        var saleAttrName = $(this).attr('sale-attr-name');
                        var split = val.split('-');
                        var saleAttrValueId =split[0];
                        var saleAttrValueName =split[1];
                        var skuSaleAttrValue={
                            saleAttrId:saleAttrId,
                            saleAttrValueId:saleAttrValueId,
                            saleAttrName:saleAttrName,
                            saleAttrValueName:saleAttrValueName
                        }
                        skuSaleAttrValues.push(skuSaleAttrValue);
                    }
                });
                var skuAttrValues = new Array();
                $('#saveSkuBody .baseAttrs select[name="baseAttr"]').each(function () {
                    var valueId = $(this).val();
                    if (valueId!='请选择'){
                        var attrId=$(this).attr('attr-id');
                        var skuAttrValue={
                            attrId:attrId,
                            valueId:valueId
                        }
                        skuAttrValues.push(skuAttrValue);
                    }
                });
                var sku={
                    productId:spuId,
                    price:price,
                    skuName:skuName,
                    skuDesc:skuDesc,
                    weight:weight,
                    catalog3Id:catalog3Id,
                    skuDefaultImg:skuDefaultImg,
                    skuImages:skuImages,
                    skuAttrValues:skuAttrValues,
                    skuSaleAttrValues:skuSaleAttrValues
                }
                var dataJson = JSON.stringify(sku);
                apis.productApis.saveSku(dataJson,function (result) {
                    if (result.code==code.SUCCESS){
                        htmlForEditSkuBodySkuList(spuId);
                        $('#saveSkuModal').modal('hide');
                    } else {
                        $('#tipsModal .modal-body').html('删除失败');
                        $('#tipsModal').modal('show');
                    }
                },null,null);
            });
        }

        //todo
        //html修改方法
        function htmlForSaveSkuBodyBaseAttrs(catalog3Id) {
            apis.productApis.listBaseAttrsByCatalog3Id(catalog3Id, function (result) {
                var html = '';
                if (result.code == code.SUCCESS) {
                    var data = result.data;
                    $.each(data, function (i, item) {
                        var optionHtml = '';
                        var baseAttrValues = item.baseAttrValues;
                        if (baseAttrValues!=undefined&&baseAttrValues!=null){
                            $.each(baseAttrValues, function (j, itemj) {
                                optionHtml += '<option value="' + itemj.id + '">' + itemj.valueName + '</option>';
                            });
                        }

                        if (optionHtml!='') {
                            html += '<div class="form-group attr-item">\n' +
                                '      <label>' + item.attrName + '</label>\n' +
                                '      <select class="form-control" name="baseAttr" attr-id="'+item.id+'">\n' +
                                '          <option>请选择</option>\n' + optionHtml +
                                '      </select>\n' +
                                '  </div>';
                        }
                    });
                }
                $('#saveSkuBody .baseAttrs').html(html);
            }, null, null);
        }

        function htmlForSaveSkuBodySaleAttrs(spuId) {
            apis.productApis.listSaleAttrsByProductId(spuId, function (result) {
                var html = '';
                if (result.code == code.SUCCESS) {
                    var data = result.data;
                    $.each(data, function (i, item) {
                        var optionHtml = '';
                        var productSaleAttrValues = item.productSaleAttrValues;
                        if (productSaleAttrValues!=undefined&&productSaleAttrValues!=null){
                            $.each(productSaleAttrValues, function (j, itemj) {
                                optionHtml += '<option value="' + itemj.id+'-'+itemj.saleAttrValueName + '">' + itemj.saleAttrValueName + '</option>';
                            });
                        }

                        if (optionHtml!='') {
                            html += '<div class="form-group attr-item">\n' +
                                '      <label>' + item.saleAttrName + '</label>\n' +
                                '      <select class="form-control" name="saleAttr" sale-attr-id="'+item.saleAttrId+'" sale-attr-name="'+item.saleAttrName+'">\n' +
                                '          <option>请选择</option>\n' + optionHtml +
                                '      </select>\n' +
                                '  </div>';
                        }
                    });
                }
                $('#saveSkuBody .saleAttrs').html(html);
            }, null, null);
        }

        function htmlForSaveSkuBodyImgCards(spuId) {
            apis.productApis.listProductImagesByProductId(spuId, function (result) {
                var html = '';
                if (result.code == code.SUCCESS) {
                    $.each(result.data, function (i, item) {
                        html += '<div class="card" image-id="' + item.id + '" img-url="' + item.imgUrl + '">\n' +
                            '      <div class="card-img-top-box" has-image="true">\n' +
                            '          <img src="' + item.imgUrl + '"\n' +
                            '               class="card-img-top" alt="' + item.imgName + '">\n' +
                            '      </div>\n' +
                            '      <div class="card-body">\n' +
                            '          <input type="radio" name="skuDefaultImgId" value="' + item.id + '">设为默认\n' +
                            '      </div>\n' +
                            '  </div>';
                    });
                }
                $('#saveSkuBody .imgCards').html(html);
            }, null, null);
        }

        function htmlForEditSkuBodySkuList(spuId) {
            apis.productApis.listSkuInfosByProductId(spuId,function (result) {
                var html='';
                if (result.code==code.SUCCESS){
                    $.each(result.data,function (i, item) {
                        html+='<tr>\n' +
                            '      <th>'+(i+1)+'</th>\n' +
                            '      <td>'+item.id+'</td>\n' +
                            '      <td>'+item.skuName+'</td>\n' +
                            '      <td>\n' +
                            '          <button type="button" class="btn btn-danger removeSkuBtn" sku-id="'+item.id+'">删除</button>\n' +
                            '      </td>\n' +
                            '  </tr>';
                    });
                }
                $('#editSkuBody .skuList tbody').html(html);
            },null,null);
        }

        function htmlForSpuManageBodySpuList(catalog3Id) {
            apis.productApis.listProductInfosByCatalog3Id(catalog3Id,function (result) {
                var html='';
                if (result.code==code.SUCCESS) {
                    $.each(result.data,function (i, item) {
                        html+='<tr>\n' +
                            '      <th style="width: 6rem">'+(i+1)+'</th>\n' +
                            '      <td style="width: 6rem">'+item.id+'</td>\n' +
                            '      <td style="width: 8rem">'+item.productName+'</td>\n' +
                            '      <td>'+item.description+'</td>\n' +
                            '      <td style="width: 12rem">\n' +
                            '          <button type="button" class="btn btn-primary editSkuBtn" spu-id="'+item.id+'" spu-name="'+item.productName+'" fragment="#editSkuBody">编辑SKU</button>\n' +
                            '          <button type="button" class="btn btn-danger removeSpuBtn" spu-id="'+item.id+'">删除</button>' +
                            '      </td>' +
                            '  </tr>';
                    });
                }
                $('#spuManageBody .spuList tbody').html(html);
            },null,null);
        }

        function htmlForSpuManageBodyCatalog3(catalog2Id) {
            var listBaseCatalog3sByCatalog2Id = listBaseCatalog3sByCatalog2IdData[catalog2Id];
            var html='<option>请选择</option>';
            if (listBaseCatalog3sByCatalog2Id!=undefined&&listBaseCatalog3sByCatalog2Id!=null){
                $.each(listBaseCatalog3sByCatalog2Id,function (i, item) {
                    html += '<option value="' + item.id + '">' + item.name + '</option>';
                });
            }
            $('#spuManageBody .catalog3 select[name="catalog3Id"]').html(html);
        }

        function htmlForSpuManageBodyCatalog2(catalog1Id) {
            var listBaseCatalog2sByCatalog1Id = listBaseCatalog2sByCatalog1IdData[catalog1Id];
            var html='<option>请选择</option>';
            if (listBaseCatalog2sByCatalog1Id!=undefined&&listBaseCatalog2sByCatalog1Id!=null){
                $.each(listBaseCatalog2sByCatalog1Id,function (i, item) {
                    html += '<option value="' + item.id + '">' + item.name + '</option>';
                });
            }
            $('#spuManageBody .catalog2 select[name="catalog2Id"]').html(html);
        }

        function htmlForSpuManageBodyCatalog1() {
            var html='<option>请选择</option>';
            if (listBaseCatalog1sData!=undefined&&listBaseCatalog1sData!=null){
                $.each(listBaseCatalog1sData,function (i, item) {
                    html+='<option value="' + item.id + '">' + item.name + '</option>';
                });
            }
            $('#spuManageBody .catalog1 select[name="catalog1Id"]').html(html);
        }
    }

    //初始化添加SPU
    function initSaveSpuBody() {
        initCatalog1();

        //初始化一级分类
        function initCatalog1() {
            if (listBaseCatalog1sData==undefined||listBaseCatalog2sByCatalog1IdData==null){
                apis.productApis.listBaseCatalog1s(function (result) {
                    if (result.code==code.SUCCESS){
                        listBaseCatalog1sData=result.data;
                    }
                    htmlForSaveSpuBodyCatalog1();
                },null,null);
            } else {
                htmlForSaveSpuBodyCatalog1();
            }
        }

        //事件绑定
        $('#saveSpuBody .catalog1 select[name="catalog1Id"]').on('change',function () {
            var catalog1Id = $(this).val();
            if (catalog1Id=='请选择') {
                return false;
            }
            $('#saveSpuBody .catalog3 select[name="catalog3Id"]').val("请选择");
            var listBaseCatalog2sByCatalog1Id = listBaseCatalog2sByCatalog1IdData[catalog1Id];
            if (listBaseCatalog2sByCatalog1Id==undefined||listBaseCatalog2sByCatalog1Id==null){
                apis.productApis.listBaseCatalog2sByCatalog1Id(catalog1Id,function (result) {
                    if (result.code==code.SUCCESS){
                        listBaseCatalog2sByCatalog1IdData[catalog1Id]=result.data;
                    }
                    htmlForSaveSpuBodyCatalog2(catalog1Id);
                },null,null);
            } else {
                htmlForSaveSpuBodyCatalog2(catalog1Id);
            }
        });
        $('#saveSpuBody .catalog2 select[name="catalog2Id"]').on('change',function () {
            var catalog2Id = $(this).val();
            if (catalog2Id=='请选择') {
                return false;
            }
            var listBaseCatalog3sByCatalog2Id = listBaseCatalog3sByCatalog2IdData[catalog2Id];
            if (listBaseCatalog3sByCatalog2Id==undefined||listBaseCatalog3sByCatalog2Id==null){
                apis.productApis.listBaseCatalog3sByCatalog2Id(catalog2Id,function (result) {
                    if (result.code==code.SUCCESS){
                        listBaseCatalog3sByCatalog2IdData[catalog2Id]=result.data;
                    }
                    htmlForSaveSpuBodyCatalog3(catalog2Id);
                },null,null);
            } else {
                htmlForSaveSpuBodyCatalog3(catalog2Id);
            }
        });
        $('#saveSpuBody .imgCards').on('click',':last-child .card-img-top-box',function () {
            $fileInput.click();
        });
        $fileInput.on('change',function () {
            var dataURL=utils.getFileInputDataUrl($(this));
            var html=utils.toHtml({imgSrc:dataURL},function () {/*
                <div class="card imgCard" img-name="" img-url="">
                    <div class="card-img-top-box" has-image="true">
                        <img src="${imgSrc}"
                             class="card-img-top" alt="...">
                    </div>
                    <div class="card-body">
                        <span class="tips">正在上传...</span>
                    </div>
                </div>
            */});
            var imgCards = $('#saveSpuBody .imgCards');
            var addImgCard=imgCards.find('.addImgCard');
            var $html = $(html);
            addImgCard.remove();
            imgCards.append($html);
            imgCards.append(addImgCard);

            var formData = new FormData();  //创建一个forData
            formData.append('file', $(this)[0].files[0]);
            var thisVal = $(this).val();
            var imgName = thisVal.substring(thisVal.lastIndexOf('\\')+1,thisVal.length);
            apis.fileApis.fileUpload(formData,function (result) {
                if (result.code==code.SUCCESS){
                    $html.find('.card-body .tips').html('上传成功').css('color','#1e7e34');
                    $html.attr('img-name',imgName);
                    $html.attr('img-url',result.data);
                }else {
                    $html.find('.card-body .tips').html('上传失败').css('color','red');
                    setTimeout(function () {
                        $html.remove();
                    },1000);
                }
            },null,null);
            $(this).val('');
        });
        $('#saveSpuBody .spuSaleAttrList .addSpuSaleAttrBtn').on('click',function () {
            if (listBaseSaleAttrsData==undefined&&listBaseSaleAttrsData==null) {
                apis.productApis.listBaseSaleAttrs(function (result) {
                    if (result.code == code.SUCCESS) {
                        listBaseSaleAttrsData = result.data;
                    }
                    htmlForSaveSpuBodySpuSaleAttrList(toHtmlBaseSaleAttrOptions());
                }, function () {
                    htmlForSaveSpuBodySpuSaleAttrList('');
                }, null);
            }else{
                htmlForSaveSpuBodySpuSaleAttrList(toHtmlBaseSaleAttrOptions());
            }

            function toHtmlBaseSaleAttrOptions(){
                var html = '<option>请选择</option>';
                if (listBaseSaleAttrsData != undefined && listBaseSaleAttrsData != null) {
                    $.each(listBaseSaleAttrsData, function (i, item) {
                        html += '<option value="' + item.id + '">' + item.name + '</option>';
                    });
                }
                return html;
            }
        });
        $('#saveSpuBody .spuSaleAttrList').on('click','.removeSpuSaleAttrBtn',function () {
            $(this).parent().parent().remove();
            initSaveSpuBodySpuSaleAttrListTrIndex();
        });
        $('#saveSpuBody .spuSaleAttrList').on('keyup','.spuSaleAttrValueInput',function (event) {
            if(event.keyCode ==13&&$(this).val()!=''){
                $(this).parent().append('<input type="text" class="form-control spuSaleAttrValueInput" name="spuSaleAttrValue"> ');
                $(this).next().focus();
                $(this).attr('readonly','true');
            }
        });
        $('#saveSpuBody .spuSaleAttrList').on('click','.spuSaleAttrValueInput',function (){
            if (utils.existAttr($(this),'readonly')) {
                $(this).remove();
            }
        });
        $('#saveSpuBody .saveSpuBtn').on('click',function () {
            var $saveSpuBody = $('#saveSpuBody');
            var catalog3Id = $saveSpuBody.find('select[name="catalog3Id"]').val();
            var spuName = $saveSpuBody.find('input[name="spuName"]').val();
            var description = $saveSpuBody.find('textarea[name="description"]').val();
            var imgUrls = new Array();
            $saveSpuBody.find('.imgCards .imgCard').each(function (i) {
                var imgName = $(this).attr('img-name');
                var imgUrl = $(this).attr('img-url');
                var spuImage={
                    imgName:imgName,
                    imgUrl:imgUrl
                }
                imgUrls[i]=spuImage;
            });
            var spuSaleAttrs = new Array();
            $saveSpuBody.find('.spuSaleAttrList tbody tr').each(function (i) {
                var $selectBaseSaleAttr = $(this).find('select[name="baseSaleAttr"]');
                var saleAttrId = $selectBaseSaleAttr.val();
                var saleAttrValueName = $selectBaseSaleAttr.find('option[value="'+saleAttrId+'"]').text();
                if (saleAttrId!='请选择'){
                    var spuSaleAttrValues = new Array();
                    $(this).find('input[name="spuSaleAttrValue"]').each(function (j) {
                        if (utils.existAttr($(this),'readonly')) {
                            var saleAttrValueName = $(this).val();
                            var spuSaleAttrValue={
                                saleAttrId:saleAttrId,
                                saleAttrValueName:saleAttrValueName
                            }
                            spuSaleAttrValues[j]=spuSaleAttrValue;
                        }
                    });
                    if (spuSaleAttrValues.length>0){
                        var spuSaleAttr={
                            saleAttrId:saleAttrId,
                            saleAttrName:saleAttrValueName,
                            productSaleAttrValues:spuSaleAttrValues
                        }
                        spuSaleAttrs[i]=spuSaleAttr;
                    }
                }
            });
            var spu={
                catalog3Id:catalog3Id,
                productName:spuName,
                description:description,
                productImages:imgUrls,
                productSaleAttrs:spuSaleAttrs
            }
            var json = JSON.stringify(spu);
            apis.productApis.saveProduct(json,function (result) {
                if (result.code==code.SUCCESS){
                    $('#tipsModal .modal-body').html('保存成功');
                    $('#tipsModal').modal('show');
                    $('#spuManageBody .catalog3 select[name="catalog3Id"]').change();
                } else {
                    $('#tipsModal .modal-body').html('保存失败');
                    $('#tipsModal').modal('show');
                }
            },null,null);
        });

        //todo
        //html修改方法
        function initSaveSpuBodySpuSaleAttrListTrIndex() {
            $('#saveSpuBody .spuSaleAttrList tbody').children().each(function (i) {
                $(this).find('th').html(i+1);
            });
        }
        function htmlForSaveSpuBodySpuSaleAttrList(baseSaleAttrOptionsHtml) {
            var html=utils.toHtml({baseSaleAttrOptionsHtml:baseSaleAttrOptionsHtml},function () {/*
                <tr>
                    <th>1</th>
                    <td>
                        <select class="form-control baseSaleAttrSelect" name="baseSaleAttr">
                            ${baseSaleAttrOptionsHtml}
                        </select>
                    </td>
                    <td>
                        <input type="text" class="form-control spuSaleAttrValueInput" name="spuSaleAttrValue">
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger removeSpuSaleAttrBtn">删除</button>
                    </td>
                </tr>
            */});
            $('#saveSpuBody .spuSaleAttrList tbody').append(html);
            initSaveSpuBodySpuSaleAttrListTrIndex();
        }

        function htmlForSaveSpuBodyCatalog3(catalog2Id) {
            var listBaseCatalog3sByCatalog2Id = listBaseCatalog3sByCatalog2IdData[catalog2Id];
            var html='<option>请选择</option>';
            if (listBaseCatalog3sByCatalog2Id!=undefined&&listBaseCatalog3sByCatalog2Id!=null){
                $.each(listBaseCatalog3sByCatalog2Id,function (i, item) {
                    html += '<option value="' + item.id + '">' + item.name + '</option>';
                });
            }
            $('#saveSpuBody .catalog3 select[name="catalog3Id"]').html(html);
        }

        function htmlForSaveSpuBodyCatalog2(catalog1Id) {
            var listBaseCatalog2sByCatalog1Id = listBaseCatalog2sByCatalog1IdData[catalog1Id];
            var html='<option>请选择</option>';
            if (listBaseCatalog2sByCatalog1Id!=undefined&&listBaseCatalog2sByCatalog1Id!=null){
                $.each(listBaseCatalog2sByCatalog1Id,function (i, item) {
                    html += '<option value="' + item.id + '">' + item.name + '</option>';
                });
            }
            $('#saveSpuBody .catalog2 select[name="catalog2Id"]').html(html);
        }

        function htmlForSaveSpuBodyCatalog1() {
            var html='<option>请选择</option>';
            if (listBaseCatalog1sData!=undefined&&listBaseCatalog1sData!=null){
                $.each(listBaseCatalog1sData,function (i, item) {
                    html+='<option value="' + item.id + '">' + item.name + '</option>';
                });
            }
            $('#saveSpuBody .catalog1 select[name="catalog1Id"]').html(html);
        }
    }
    
    
});