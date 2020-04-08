<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../../excel/excelUploadModal.ftlh" as eU>
<#setting locale="uk">

<@htmlPad.page "Basket">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                    data-target="#basketAddModal">
                <i class="far fa-plus-square"></i> Додати новий кошик
            </button>
            <@addModal.basketAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <a class="btn btn-outline-danger mr-5 fixedBtnHeight" href="/basket/graph" role="button">
                <i class="fas fa-chart-pie"></i></a>
            <button type="button" class="btn btn-success mr-2 fixedBtnHeight" data-toggle="modal"
                    data-target="#BasketExcelUploadModal">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i>
            </button>
            <@eU.excelUpload tableNameEn="Basket" tableNameUk="кошиків"/>
            <a class="btn btn-success fixedBtnHeight" href="/basket/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#basketSearch" aria-expanded="false" aria-controls="basketSearch">
                Знайти кошик
            </button>
            <div class="collapse form-group" id="basketSearch">
                <form method="get" action="/basket">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="number" min="1" class="form-control" name="employeeId"
                                   value="${RequestParameters.employeeId!}" placeholder="ID продавця"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="employeeFirstName"
                                   value="${RequestParameters.employeeFirstName!}" placeholder="Ім'я продавця"/>
                        </div>
                        <div class="col">
                            <input type="text" class="form-control" name="employeeSecondName"
                                   value="${RequestParameters.employeeSecondName!}" placeholder="Прізвище продавця"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="employeeShopAddress"
                                   value="${RequestParameters.employeeShopAddress!}" placeholder="Адреса магазину"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="number" min="1" class="form-control" name="clientId"
                                   value="${RequestParameters.clientId!}" placeholder="ID покупця"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="clientFirstName"
                                   value="${RequestParameters.clientFirstName!}" placeholder="Ім'я покупця"/>
                        </div>
                        <div class="col">
                            <input type="text" class="form-control" name="clientSecondName"
                                   value="${RequestParameters.clientSecondName!}" placeholder="Прізвище покупця"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <label class="d-inline ml-2" for="dateTimeStr">Дата/час покупки:</label>
                            <input type="datetime-local" class="form-control" name="dateTime"
                                   value="${RequestParameters.dateTime!}" placeholder="Дата/Час покупки"/>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger ml-md-2 my-2 float-right">
                        <i class="fa fa-search"></i>
                    </button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </div>

    <div class="mt-4">
        <div class="row">
            <div class="col">
                <@eO.errorOutput errorMessage!/>
            </div>
        </div>
        <#if baskets?has_content>
            <div class="h1">Список кошиків:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Id</th>
                    <th scope="col">Id продавця</th>
                    <th scope="col">Ім'я продавця</th>
                    <th scope="col">Прізвище продавця</th>
                    <th scope="col">Адреса магазину</th>
                    <th scope="col">Id покупця</th>
                    <th scope="col">Ім'я покупця</th>
                    <th scope="col">Прізвище покупця</th>
                    <th scope="col">Час покупки</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list baskets as basket>
                    <tr>
                        <th class="text-danger" scope="row">${basket.id}</th>
                        <#if basket.employee??>
                            <td>${basket.employee.id}</td>
                            <td>${basket.employee.firstName}</td>
                            <td>${basket.employee.secondName}</td>
                            <#if (basket.employee.shop)??>
                                <td>${basket.employee.shop.address}</td>
                            <#else>
                                <td>Видалено</td>
                            </#if>
                        <#else>
                            <td>Видалено</td>
                            <td>Видалено</td>
                            <td>Видалено</td>
                            <td>Видалено</td>
                        </#if>

                        <#if basket.client??>
                            <td>${basket.client.id}</td>
                            <td>${basket.client.firstName}</td>
                            <td>${basket.client.secondName}</td>
                        <#else>
                            <td>Видалено</td>
                            <td>Видалено</td>
                            <td>Видалено</td>
                        </#if>
                        <td>${basket.dateTime.format('yyyy-MM-dd HH:mm')}</td>
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#basketEditModal${basket.id?c}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.basketEdit id="${basket.id?c}"
                            currentEmployeeId = "${(basket.employee??)?then(basket.employee.id, '')}"
                            currentClientId = "${(basket.client??)?then(basket.client.id, '')}"
                            currentDateTime = "${basket.dateTime}"
                            />
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${basket.id?c}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${basket.id?c}"
                            deletionLink="/basket/delete/${basket.id?c}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного кошика.</div>
        </#if>
    </div>
</@htmlPad.page>