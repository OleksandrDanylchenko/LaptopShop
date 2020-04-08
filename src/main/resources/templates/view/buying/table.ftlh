<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../../excel/excelUploadModal.ftlh" as eU>

<@htmlPad.page "Buying">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                    data-target="#buyingAddModal">
                <i class="far fa-plus-square"></i> Додати нову покупку
            </button>
            <@addModal.buyingAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <button type="button" class="btn btn-success mr-2 fixedBtnHeight" data-toggle="modal"
                    data-target="#BuyingExcelUploadModal">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i>
            </button>
            <@eU.excelUpload tableNameEn="Buying" tableNameUk="записів про покупки"/>
            <a class="btn btn-success fixedBtnHeight" href="/buying/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
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
                            <label class="d-inline ml-2" for="dateTimeStr">Дата/Час покупки:</label>
                            <input type="datetime-local" min="2020-02-16T00:00" class="form-control"
                                   name="dateTimeStr" value="${RequestParameters.dateTimeStr!}"/>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger float-right">
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
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#buyingEditModal${buying.id?c}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.buyingEdit id="${buying.id?c}"
                            currentBasketId="${(buying.basket??)?then(buying.basket.id, '')}"
                            currentLaptopModel="${(buying.laptop??)?then(buying.laptop.label.model, '')}"
                            currentTotalPrice="${buying.totalPrice?c}"
                            />
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${buying.id?c}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${buying.id?c}"
                            deletionLink="/buying/delete/${buying.id?c}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <h3>Не знайдено жодної покупки.</h3>
        </#if>
    </div>
</@htmlPad.page>