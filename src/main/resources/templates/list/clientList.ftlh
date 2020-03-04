<#import "../parts/htmlPad.ftlh" as pad>
<#setting locale="uk">

<@pad.page "Client">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-danger" href="/client/add" role="button">
                <i class="far fa-plus-square"></i> Додати нового клієнта</a>
        </div>
        <div class="col-sm-1 col-3">
            <a class="btn btn-success" href="/client/importExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i></a>
        </div>
        <div class="col-sm-1 col-3">
            <a class="btn btn-success" href="/client/exportExcel" role="button">
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
                </form>
            </div>
        </div>
    </div>

    <div class="row mt-5">
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
                        <td><a class="btn btn-outline-dark" href="/client/edit/${client.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-danger" href="/client/delete/${client.id}"
                               role="button" onclick="return confirm('Ви впевнені?')"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного покупця.</div>
        </#if>
    </div>
</@pad.page>