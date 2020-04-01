<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#setting locale="uk">

<@htmlPad.page "Client">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                    data-target="#clientAddModal">
                <i class="far fa-plus-square"></i> Додати нового клієнта
            </button>
            <@addModal.clientAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <a class="btn btn-success mr-2" id="fixedBtnHeight" href="/client/importExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i></a>
            <a class="btn btn-success" id="fixedBtnHeight" href="/client/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#clientSearch" aria-expanded="false" aria-controls="clientSearch">
                Знайти клієнта
            </button>
            <div class="collapse form-group" id="clientSearch">
                <form method="get" action="/client">
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
                            <label class="d-inline ml-2" for="dateReg">Дата реєстрації:</label>
                            <input type="date" class="form-control" name="dateReg" min="2019-02-16"
                                   value="${RequestParameters.dateReg!}"/>
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
        <#if clients?has_content>
            <div class="h1">Список клієнтів:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Id</th>
                    <th scope="col">Ім'я</th>
                    <th scope="col">Прізвище</th>
                    <th scope="col">Дата реєстрації</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list clients as client>
                    <tr>
                        <th class="text-danger" scope="row">${client.id}</th>
                        <td>${client.firstName}</td>
                        <td>${client.secondName}</td>
                        <td>${client.dateReg}</td>
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#clientEditModal${client.id}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.clientEdit id="${client.id}"clientFirstName="${client.firstName}"
                            clientSecondName="${client.secondName}" clientDateReg="${client.dateReg?iso_utc}"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${client.id}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${client.id}"
                            deletionLink="/client/delete/${client.id}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного покупця.</div>
        </#if>
    </div>
</@htmlPad.page>