<#import "../parts/htmlPad.ftlh" as pad>
<#setting locale="uk">

<@pad.page "Availability">
    <div>
        <p><a href="/availability/add">Додати новий запис про наявність</a></p>
    </div>

    <div>
        <p>Знайти запис про наявність за критеріями:</p>
        <form method="get" action="/availability">
            <label>Модель ноутбука: <input type="text" name="laptopModel"
                                           value="${RequestParameters.laptopModel!}"/></label>
            <label>Ціна: <input type="number" step="1" min="5000" name="price"
                                value="${RequestParameters.price!}"/></label>
            <label>Кількість: <input type="number" step="1" min="1" name="quantity"
                                     value="${RequestParameters.quantity!}"/></label>
            <label>Адреса магазину: <input type="text" name="shopAddress"
                                           value="${RequestParameters.shopAddress!}"/></label>
            <p><label>Дата початку продаж: <input type="date" name="dateStart" min="2019-02-16"
                                                  value="${RequestParameters.dateStart!}"></label>
                <label>Дата закінчення продаж: <input type="date" name="dateEnd" min="2019-02-16"
                                                      value="${RequestParameters.dateEnd!}"></label></p>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <div class="row mt-5">
        <#if availabilities?has_content>
            <div class="h2">Список записів про наявність:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Id</th>
                    <th scope="col">Модель</th>
                    <th scope="col">Ціна</th>
                    <th scope="col">Кількість</th>
                    <th scope="col">Номер магазину</th>
                    <th scope="col">Адреса магазину</th>
                    <th scope="col">Початок продаж</th>
                    <th scope="col">Закінчення продаж</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <#list availabilities as availability>
                    <tr>
                        <th class="text-danger" scope="row">${availability.id}</th>
                        <td>${availability.laptop.label.model}</td>
                        <td>${availability.price}</td>
                        <td>${availability.quantity}</td>
                        <td>${availability.shop.id}</td>
                        <td>${availability.shop.address}</td>
                        <td>${availability.dateStart}</td>
                        <td>${availability.dateEnd}</td>
                        <td><a href="/availability/edit/${availability.id}">Edit</a></td>
                        <td><a href="/availability/delete/${availability.id}" onclick="return confirm('Ви впевнені?')">Delete</a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <h3>Не знайдено жодного запису про наявність.</h3>
        </#if>
    </div>
</@pad.page>