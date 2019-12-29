/*
全局变量区
 */
var apis = new Apis();
var utils = new Utils();
var listBaseCatalog1sData='';
var listBaseCatalog2sByCatalog1IdData={};
var listBaseCatalog3sByCatalog2IdData={};
var listBaseAttrInfosByCatalog3IdData={};

/*
当前页面通用方法区
 */
//初始化面包屑
function initBreadcrumb(breadcrumbs) {
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
function switchManageFragment(fragment){
    $('#manage #manageBody>*').hide();
    $(fragment).show();
}


/**
 * 初始化
 */
$(function () {
    //初始化全局变量 listBaseCatalog1sData
    apis.productApis.listBaseCatalog1s(function (result) {
        if (result.code=code.SUCCESS){
            listBaseCatalog1sData=result.data;
        }
    },null,null);

    //初始化标题栏面包屑
    $('#titleBar #breadcrumb').on('click','a',function (e) {
        e.defaultPrevented();
    });


});



/*
管理界面侧边栏
 */
$(function () {
    //初始化管理界面侧边栏
    $('#sideBar .item-body').slideUp();

    $('#sideBar .item-header').on('click',function () {
        var nextBody = $(this).next();
        nextBody.slideToggle();
        $('#sideBar .item-body').each(function (i) {
            if (!$(this).is(nextBody)){
                $(this).slideUp();
            }
        });
    });

    $('#sideBar .item-body>div').on('click',function () {
        $('#sideBar .item-body>div').removeClass('sideBarItemBodyItemClick');
        $(this).addClass('sideBarItemBodyItemClick');
        var fragment = $(this).attr('fragment');
        switchManageFragment(fragment);
        switch (fragment) {
            case '#baseAttrListBody':
                initBreadcrumb(['基本信息管理','平台属性列表']);
                break;
            case '#saveBaseAttrInfoBody':
                initBreadcrumb(['基本信息管理','添加平台属性']);
                break;
            case '#spuManageBody':
                initBreadcrumb(['商品信息管理','商品属性SPU管理']);
                break;
            case '#saveSpuBody':
                initBreadcrumb(['商品信息管理','添加SPU']);
                break;
        }
    });
});


/*
平台属性列表
 */
$(function () {
    //编辑平台属性值按钮绑定
    $('#baseAttrListBody').on('click','.editBaseAttrValueBtn',function () {
        var fragment = $(this).attr('fragment');
        $('#manage #manageBody>*').hide();
        $(fragment).show();
        var baseAttrId = $(this).attr('base-attr-id');
        var baseAttrName = $(this).attr('base-attr-name');
        $(fragment).attr('base-attr-id',baseAttrId);
        $(fragment+' .baseAttrName').html(baseAttrName);
        initBreadcrumb(['基本信息管理','平台属性列表','编辑平台属性值']);
    });

    //初始化一级分类
    function initBaseAttrListBodySelectCatalog1Id() {
        var html = '<option value="请选择">请选择</option>';
        $.each(listBaseCatalog1sData, function (i, item) {
            html += '<option value="' + item.id + '">' + item.name + '</option>';
        });
        $('#baseAttrListBody .catalog select[name="catalog1Id"]').html(html);
    }
    function initBaseAttrListBody(){
        if (listBaseCatalog1sData==''){
            apis.productApis.listBaseCatalog1s(function (result) {
                if (result.code=code.SUCCESS){
                    listBaseCatalog1sData=result.data;
                    initBaseAttrListBodySelectCatalog1Id();
                }
            },null,null);
        }else {
            initBaseAttrListBodySelectCatalog1Id();
        }
    }
    $('#sideBar .item-body>div').on('click',function () {
        var fragment = $(this).attr('fragment');
        if (fragment=='#baseAttrListBody'){
            initBaseAttrListBody();
        }
    });

    //初始化二级分类
    function initListBaseCatalog2sByCatalog1Id(jqObject,catalog1Id) {
        var listBaseCatalog2sByCatalog1Id = listBaseCatalog2sByCatalog1IdData[catalog1Id];
        var html = '<option value="请选择">请选择</option>';
        $.each(listBaseCatalog2sByCatalog1Id, function (i, item) {
            html += '<option value="' + item.id + '">' + item.name + '</option>';
        });
        jqObject.html(html);
    }
    $('#baseAttrListBody .catalog').on('change','select[name="catalog1Id"]',function () {
        var catalog1Id = $(this).val();
        if (catalog1Id=="请选择") {
            return false;
        }
        var jqObj=$(this).parent().next().find('select[name="catalog2Id"]');
        var listBaseCatalog2sByCatalog1Id = listBaseCatalog2sByCatalog1IdData[catalog1Id];
        if (listBaseCatalog2sByCatalog1Id==undefined){
            apis.productApis.listBaseCatalog2sByCatalog1Id(catalog1Id,function (result) {
                if (result.code=code.SUCCESS){
                    listBaseCatalog2sByCatalog1IdData[catalog1Id]=result.data;
                    initListBaseCatalog2sByCatalog1Id(jqObj,catalog1Id);
                }
            },null,null);
        }else {
            initListBaseCatalog2sByCatalog1Id(jqObj,catalog1Id);
        }
    });

    //初始化三级分类
    function initListBaseCatalog3sByCatalog2Id(jqObject,catalog2Id){
        var listBaseCatalog3sByCatalog2Id = listBaseCatalog3sByCatalog2IdData[catalog2Id];
        var html = '<option value="请选择">请选择</option>';
        $.each(listBaseCatalog3sByCatalog2Id, function (i, item) {
            html += '<option value="' + item.id + '">' + item.name + '</option>';
        });
        jqObject.html(html);
    }
    $('#baseAttrListBody .catalog').on('change','select[name="catalog2Id"]',function () {
        var catalog2Id = $(this).val();
        if (catalog2Id=="请选择") {
            return false;
        }
        var jqObj=$(this).parent().next().find('select[name="catalog3Id"]');
        var listBaseCatalog3sByCatalog2Id = listBaseCatalog3sByCatalog2IdData[catalog2Id];
        if (listBaseCatalog3sByCatalog2Id==undefined){
            apis.productApis.listBaseCatalog3sByCatalog2Id(catalog2Id,function (result) {
                if (result.code=code.SUCCESS){
                    listBaseCatalog3sByCatalog2IdData[catalog2Id]=result.data;
                    initListBaseCatalog3sByCatalog2Id(jqObj,catalog2Id);
                }
            },null,null);
        }else {
            initListBaseCatalog3sByCatalog2Id(jqObj,catalog2Id);
        }
    });

    function htmlBaseAttrListBodyBaseAttrListTbody(catalog3Id){
        var listBaseAttrInfosByCatalog3Id = listBaseAttrInfosByCatalog3IdData[catalog3Id];
        var html='';
        if (listBaseAttrInfosByCatalog3Id!=undefined) {
            $.each(listBaseAttrInfosByCatalog3Id, function (i, item) {
                html += utils.toHtml({
                    index: i+1,
                    id: item.id,
                    attrName: item.attrName
                }, function () {/*
              <tr>
                    <th>${index}</th>
                    <td>${id}</td>
                    <td>${attrName}</td>
                    <td>
                        <button type="button" class="btn btn-primary editBaseAttrValueBtn" fragment="#editBaseAttrValueBody" base-attr-id="${id}" base-attr-name="${attrName}">编辑平台属性值</button>
                        <button type="button" class="btn btn-danger removeBaseAttrBtn" base-attr-id="${id}">删除</button>
                    </td>
                </tr>
                */
                });
            });
        }
        $('#baseAttrListBody .baseAttrList tbody').html(html);
    }
    //todo
    $('#baseAttrListBody .catalog').on('change','select[name="catalog3Id"]',function () {
        var catalog3Id = $(this).val();
        if (catalog3Id=="请选择") {
            return false;
        }
        var listBaseAttrInfosByCatalog3Id = listBaseAttrInfosByCatalog3IdData[catalog3Id];
        if (listBaseAttrInfosByCatalog3Id==undefined){
            apis.productApis.listBaseAttrInfosByCatalog3Id(catalog3Id,function (result) {
                if (result.code==code.SUCCESS){
                    listBaseAttrInfosByCatalog3IdData[catalog3Id]=result.data;
                }
                htmlBaseAttrListBodyBaseAttrListTbody(catalog3Id);
            },null,null);
        } else {
            htmlBaseAttrListBodyBaseAttrListTbody(catalog3Id);
        }
    })

});

$(function () {



    //添加SPU
    $('#saveSpuBody .imgCards').on('click','.card-img-top-box',function () {
        var hasImg = $(this).attr('has-image');
        if (hasImg=='true'){
            $(this).parent().remove();
        }else {
            $('#imgUp').click();
        }
    });
    $('#imgUp').on('change',function () {
        var dataURL=getFileInputDataUrl($(this));
        var html=toHtml({imgSrc:dataURL},function () {/*
                <div class="card">
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
        imgCards.html(html+imgCards.html());

        var tips = $(html).find('.tips');
        var formData = new FormData();  //创建一个forData
        formData.append('file', $('#imgUp')[0].files[0]);
        //todo 文件上传

        $(this).val('');
    });

    var baseSaleAttr=null;
    $('#saveSpuBody .addSpuSaleAttrBtn').on('click',function () {
        if (baseSaleAttr==null){
            //todo 获取spu销售属性
        }

        var baseSaleAttrOptionHtml='';
        var html=toHtml({
            baseSaleAttrOption:baseSaleAttrOptionHtml
        },function () {/*
            <tr>
                <th>1</th>
                <td>
                    <select class="form-control baseSaleAttrSelect" name="baseSaleAttr">
                        <option>请选择</option>
                        ${baseSaleAttrOption}
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
        initSaveSpuBodySpuSaleAttrListIndex();
    });
    function initSaveSpuBodySpuSaleAttrListIndex(){
        $('#saveSpuBody .spuSaleAttrList tbody tr').each(function (index) {
            $(this).children().eq(0).html(index+1);
        });
    }

    $('#saveSpuBody .spuSaleAttrList tbody').on('click','.removeSpuSaleAttrBtn',function () {
        $(this).parent().parent().remove();
        initSaveSpuBodySpuSaleAttrListIndex();
    });

    $('#saveSpuBody .spuSaleAttrList tbody').on('keyup','.spuSaleAttrValueInput',function (event) {
        if(event.keyCode ==13&&$(this).val()!=''){
            $(this).parent().append('<input type="text" class="form-control spuSaleAttrValueInput" name="spuSaleAttrValue"> ');
            $(this).next().focus();
            $(this).attr('readonly','true');
        }
    });

    $('#saveSpuBody .spuSaleAttrList tbody').on('click','.spuSaleAttrValueInput[readonly]',function (event) {
        $(this).remove();
    });


    /**
     * #manage
     * 管理界面
     */

    $('#sideBar .item-header').eq(0).click();
    $('#sideBar .item-body').eq(0).children().eq(0).click();

    /**
     * #manage #spuManageBody
     * 管理界面商品属性spu管理
     */

    $('#spuManageBody').on('click','.editSkuBtn',function () {
        var spuId = $(this).attr('spu-id');
        var spuName = $(this).attr('spu-name');
        var fragment = $(this).attr('fragment');
        switchManageFragment(fragment);
        $(fragment).attr('spu-id',spuId);
        $(fragment+' .spuName').html(spuName);
        initBreadcrumb(['商品信息管理','商品属性SPU管理','编辑SKU']);
    });

    /**
     * #manage #editSkuBody #editSkuBody
     * 管理界面编辑SKU添加sku
     */

});
