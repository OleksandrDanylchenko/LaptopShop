<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../../excel/excelUploadModal.ftlh" as eU>
<#setting locale="uk">

<@htmlPad.page "Availability">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                    data-target="#availabilityAddModal">
                <i class="far fa-plus-square"></i> Додати новий запис про наявність
            </button>
            <@addModal.availabilityAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <button type="button" class="btn btn-success mr-2" id="fixedBtnHeight" data-toggle="modal"
                    data-target="#AvailabilityExcelUploadModal">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i>
            </button>
            <@eU.excelUpload tableNameEn="Availability" tableNameUk="записів про наявність"/>
            <a class="btn btn-success mr-2" id="fixedBtnHeight" href="/availability/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i>
            </a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#availabilitySearch" aria-expanded="false" aria-controls="availabilitySearch">
                Знайти запис про наявність
            </button>
            <div class="collapse form-group" id="availabilitySearch">
                <form method="get" action="/availability">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="laptopModel"
                                   value="${RequestParameters.laptopModel!}" placeholder="Модель ноутбука"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="number" min="5000" class="form-control" name="price"
                                   value="${RequestParameters.price!}" placeholder="Ціна"/>
                        </div>
                        <div class="col">
                            <input type="number" min="1" class="form-control" name="quantity"
                                   value="${RequestParameters.quantity!}" placeholder="Кількість"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="shopAddress"
                                   value="${RequestParameters.shopAddress!}" placeholder="Адреса магазину"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <label class="d-inline ml-2" for="dateStart">Дата початку продаж:</label>
                            <input type="date" class="form-control" name="dateStart" min="2019-02-16"
                                   value="${RequestParameters.dateStart!}"/>
                        </div>
                        <div class="col">
                            <label class="d-inline ml-sm-2 ml-0" for="dateStart">Дата закінчення продаж:</label>
                            <input type="date" class="form-control" name="dateEnd" min="2019-02-16"
                                   value="${RequestParameters.dateEnd!}"/>
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
        <#if availabilities?has_content>
            <div class="row">
                <div class="col">
                    <div class="h1">Список записів про наявність:</div>
                </div>
            </div>
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
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#availabilityEditModal${availability.id?c}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.availabilityEdit id="${availability.id?c}"
                            currentLaptopModel="${availability.laptop.label.model}"
                            currentPrice="${availability.price?c}"
                            currentQuantity="${availability.quantity?c}"
                            currentShopAddress="${availability.shop.address}"
                            currentDateStart="${availability.dateStart?iso_local}"
                            currentDateEnd="${availability.dateEnd?iso_local}"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${availability.id?c}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${availability.id?c}"
                            deletionLink="/availability/delete/${availability.id?c}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного запису про наявність.</div>
        </#if>
    </div>
</@htmlPad.page>