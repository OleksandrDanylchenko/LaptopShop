<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "addModal.ftlh" as addModal>
<#import 'editModal.ftlh' as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>

<@htmlPad.page "Employee">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                    data-target="#employeeAddModal">
                <i class="far fa-plus-square"></i> Додати нового співробітника
            </button>
            <@addModal.employeeAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <a class="btn btn-success mr-2" id="fixedBtnHeight" href="/employee/importExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i></a>
            <a class="btn btn-success" id="fixedBtnHeight" href="/employee/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#employeeSearch" aria-expanded="false" aria-controls="employeeSearch">
                Знайти співробітника
            </button>
            <div class="collapse form-group" id="employeeSearch">
                <form method="get" action="/employee">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="firstName"
                                   value="${RequestParameters.firstName!}" placeholder="Ім'я"/>
                        </div>
                        <div class="col">
                            <input type="text" class="form-control" name="secondName"
                                   value="${RequestParameters.secondName!}" placeholder="Прізвище"/>
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
                            <select class="custom-select" name="isWorking">
                                <option value="" selected disabled>Статус співробітника</option>
                                <option>Працюючий</option>
                                <option>Звільнений</option>
                            </select>
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
        <#if employees?has_content>
            <div class="h1">Список співробітників:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Id</th>
                    <th scope="col">Ім'я</th>
                    <th scope="col">Прізвище</th>
                    <th scope="col">№ магазину</th>
                    <th scope="col">Адреса магазину</th>
                    <th scope="col">Статус</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list employees as employee>
                    <tr>
                        <th class="text-danger" scope="row">${employee.id}</th>
                        <td>${employee.firstName}</td>
                        <td>${employee.secondName}</td>
                        <#if employee.shop??>
                            <td>${employee.shop.id}</td>
                            <td>${employee.shop.address}</td>
                        <#else>
                            <td>Закрито</td>
                            <td>Закрито</td>
                        </#if>
                        <td>${employee.isWorking?then("Працюючий", "Звільнений")}</td>
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#employeeEditModal${employee.id}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.employeeEdit id="${employee.id}" currentFirstName="${employee.firstName}"
                            currentSecondName="${employee.secondName}"
                            currentShopAddress="${(employee.shop??)?then(employee.shop.address, '')}"
                            currentWorkingStatus="${employee.isWorking?string('Працюючий', 'Звільнений')}"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${employee.id}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${employee.id}"
                            deletionLink="/employee/delete/${employee.id}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного співробітника.</div>
        </#if>
    </div>
</@htmlPad.page>