var apis = new Apis();
var utils = new Utils();

$(function () {

    initTitleBar();
    initCatalogBar();
    initProductList();

    function initTitleBar(){
        apis.userApis.getUsernameAndNickname(function (result) {
            if (result.code==code.SUCCESS){
                console.log(result.data)
                $('#tbLogin .login').html(result.data.nickname);
            }
        },null,null);
    }

    /**
     * 分类栏
     */
    function initCatalogBar(){
        $('#catalogBar .catalog').on('mouseenter',function () {
            $(this).find('.catalog1s').show();
        });

        $('#catalogBar .catalog').on('mouseleave',function () {
            $(this).find('.catalog1s').hide();
        });

        $('#catalogBar').on('mouseenter','.catalog1',function () {
            $(this).find('.catalog2s').show();
        });
        $('#catalogBar').on('mouseleave','.catalog1',function () {
            $(this).find('.catalog2s').hide();
        });

        $('#catalogBar .catalog .catalog1s').html('<div class="catalog1">\n' +
            '                                        <span>加载中。。。</span>\n' +
            '                                     </div>');
        $('#catalogBar .catalog2s').html('<ol class="clear catalog2">\n' +
            '                                 <dt>加载中。。。</dt>\n' +
            '                              </ol>');
        apis.productApis.listBaseCatalog1s(function (result) {
            if (result.code==code.SUCCESS){
                var data=result.data;
                var html='';
                $.each(data,function (i,item) {
                    html+='<div class="catalog1" catalog1-id="'+item.id+'">\n' +
                        '      <span>'+item.name+'</span>\n' +
                        '      <div class="catalog2s">' +
                        '          <ol class="clear catalog2">\n' +
                        '               <dt>加载中。。。</dt>\n' +
                        '          </ol>'+
                        '      </div>' +
                        '   </div>';
                });
                $('#catalogBar .catalog .catalog1s').html(html);
            }
        },null,null);

        $('#catalogBar').on('mouseenter','.catalog1',function () {
            var $this = $(this);
            if (!utils.existAttr($this,'mouseenter')) {
                $this.attr('mouseenter','true');
                htmlForJqObjCatalog2s($this);
            }
        });

        function htmlForJqObjCatalog2s(jqObj) {
            var $this = jqObj;
            var catalog1Id = $this.attr('catalog1-id');
            apis.productApis.listBaseCatalog2sByCatalog1Id(catalog1Id,function (result) {
                var html='';
                if (result.code==code.SUCCESS){
                    $.each(result.data,function (i, item) {
                        html+='<ol class="clear catalog2" catalog2-id="'+item.id+'">\n' +
                            '      <dt>'+item.name+'</dt>\n' +
                            '      <dd><a>加载中。。。</a></dd>\n' +
                            '  </ol>';
                    });
                }
                $this.find('.catalog2s').html(html);
                htmlForJqObjCatalog2Dd($this);
            },null,null);
        }

        function htmlForJqObjCatalog2Dd(jqObj) {
            jqObj.find('.catalog2').each(function () {
                var $this = $(this);
                var catalog2Id = $this.attr('catalog2-id');
                apis.productApis.listBaseCatalog3sByCatalog2Id(catalog2Id,function (result) {
                    var html='';
                    if (result.code==code.SUCCESS){
                        $.each(result.data,function (i,item) {
                            html+='<dd><a href="'+item.id+'">'+item.name+'</a></dd>';
                        });
                    }
                    $this.children().first().siblings().remove();
                    $this.append(html);
                },null,null);
            });
        }
    }

    function initProductList() {
        apis.productApis.listSkuInfosGroupByProductId(function (result) {
            var html='';
            if (result.code==code.SUCCESS){
                $.each(result.data,function (i, item) {
                    html+='<div class="card" product-id="'+item.productId+'">\n' +
                        '     <div class="card-img-top-box">\n' +
                        '         <img src="'+item.skuDefaultImg+'"\n' +
                        '              class="card-img-top" alt="'+item.skuName+'">\n' +
                        '     </div>\n' +
                        '     <div class="card-body">\n' +
                        '         <h5 class="card-title line-limit-length">'+item.skuName+'</h5>\n' +
                        '         <p class="card-text line-limit-length">￥'+item.price+'</p>\n' +
                        '     </div>\n' +
                        ' </div>';
                });
            }
            // console.log(html);
            $('#productCards').html(html);
        },null,null);

        $('#productCards').on('click','.card',function () {
            var productId = $(this).attr('product-id');
            window.location.href=protocol_host.page+"/item.do?productId="+productId;
        });
    }



});