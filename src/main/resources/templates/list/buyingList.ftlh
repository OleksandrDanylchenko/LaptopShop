<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Buying">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-danger" href="/buying/add" role="button">
                <i class="far fa-plus-square"></i> Додати нову покупку</a>
        </div>
        <div class="col-1 mr-2 mr-sm-0">
            <a class="btn btn-success" href="/buying/exportExcel" role="button"><i class="fas fa-file-excel"></i></a>
        </div>
        <div class="col-lg-4 col-5">
            <button class="btn btn-dark w-100" type="button" data-toggle="collapse"
                    data-target="#buyingSearch" aria-expanded="false" aria-controls="buyingSearch">
                Знайти покупку
            </button>
            <div class="collapse form-group" id="buyingSearch">
                <form method="get" action="/buying">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="number" min="1" class="form-control" name="basketId"
                                   value="${RequestParameters.basketId!}" placeholder="ID кошику"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="laptopModel"
                                   value="${RequestParameters.laptopModel!}" placeholder="Модель ноутбуку"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="number" min="1" class="form-control" name="totalPrice"
                                   value="${RequestParameters.totalPrice!}" placeholder="Загальна ціна"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <label class="d-inline ml-2" for="dateTimeStr">Дата закінчення продаж:</label>
                            <input type="datetime-local" min="2020-02-16T00:00" class="form-control"
                                   name="dateTimeStr" value="${RequestParameters.dateTimeStr!}"
                                   placeholder="Дата/Час покупки"/>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger float-right">
                        <i class="fa fa-search"></i>
                    </button>
                </form>
            </div>
        </div>
    </div>

    <div class="row mt-5">
        <#if buyings?has_content>
            <div class="h1">Список покупок:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Id</th>
                    <th scope="col">Загальна ціна</th>
                    <th scope="col">Id кошика</th>
                    <th scope="col">Час покупки</th>
                    <th scope="col">Id ноутбуку</th>
                    <th scope="col">Модель ноутбуку</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list buyings as buying>
                    <tr>
                        <th class="text-danger" scope="row">${buying.id}</th>
                        <td>${buying.totalPrice}грн.</td>
                        <#if buying.basket??>
                            <td>${buying.basket.id}</td>
                            <td>${buying.basket.dateTime.format('yyyy-MM-dd HH:mm')}</td>
                        <#else>
                            <td>Видалено</td>
                            <td>Видалено</td>
                        </#if>

                        <#if buying.laptop??>
                            <td>${buying.laptop.id}</td>
                            <td>${buying.laptop.label.model}</td>
                        <#else>
                            <td>Видалено</td>
                            <td>Видалено</td>
                        </#if>
                        <td><a class="btn btn-outline-dark" href="/buying/edit/${buying.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-danger" href="/buying/delete/${buying.id}"
                               role="button" onclick="return confirm('Ви впевнені?')"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <h3>Не знайдено жодної покупки.</h3>
        </#if>
    </div>
</@pad.page>