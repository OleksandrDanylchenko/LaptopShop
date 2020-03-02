<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Shops">
    <div class="row">
        <div class="col-md-8">
            <a class="btn btn-outline-danger float-left" href="/shop/add" role="button">
                <i class="far fa-plus-square"></i> Додати новий магазин</a>
        </div>
        <div class="col-1">
            <a class="btn btn-success my-2 my-md-0" href="/shop/exportExcel" role="button"><i class="fas fa-file-excel"></i></a>
        </div>
        <div class="col-md-3">
            <div class="form-group" id="shopSearch">
                <form method="get" action="/shop" class="form-inline">
                    <input type="text" class="form-control" name="address" value="${RequestParameters.address!}"
                           placeholder="Адреса"/>
                    <button type="submit" class="btn btn-danger mt-2 mt-sm-0 mt-md-2 mt-xl-0
                    ml-2 ml-md-0 ml-xl-2"><i class="fa fa-search"></i></button>
                </form>
            </div>
        </div>
    </div>

    <div class="row mt-2">
        <#if shops?has_content>
            <div class="h1">Список магазинів:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Id</th>
                    <th scope="col">Адреса</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list shops as shop>
                    <tr>
                        <th class="text-danger" scope="row">${shop.id}</th>
                        <td>${shop.address}</td>
                        <td><a class="btn btn-outline-dark" href="/shop/edit/${shop.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-danger" href="/shop/delete/${shop.id}"
                               role="button" onclick="return confirm('Ви впевнені?')"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного магазину.</div>
        </#if>
    </div>
</@pad.page>