<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Shops">
    <div class="row">
        <div class="col">
            <a class="btn btn-primary float-left" href="/shop/add" role="button">Додати новий магазин</a>
        </div>

        <div class="col">
            <div class="form-group mt-2 float-right" id="shopSearch">
                <form method="get" action="/shop" class="form-inline">
                    <input type="text" class="form-control" name="address" value="${RequestParameters.address!}"
                           placeholder="Адреса" style="width: 300px"/>
                    <button type="submit" class="btn btn-primary ml-2">Шукати</button>
                </form>
            </div>
        </div>
    </div>

    <div class="row mt-2">
        <#if shops?has_content>
            <div class="h3">Список магазинів:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Адреса</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list shops as shop>
                    <tr>
                        <th scope="row">${shop.id}</th>
                        <td>${shop.address}</td>
                        <td><a href="/shop/edit/${shop.id}">Edit</a></td>
                        <td><a href="/shop/delete/${shop.id}" onclick="return confirm('Are you sure?')">Delete</a></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <h3>Не знайдено жодного магазину.</h3>
        </#if>
    </div>
</@pad.page>