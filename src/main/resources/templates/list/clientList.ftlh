<#import "../parts/htmlPad.ftlh" as pad>
<#setting locale="uk">

<@pad.page "Client">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-danger" href="/client/add" role="button">
                <i class="far fa-plus-square"></i> Додати нового клієнта</a>
        </div>

        <div class="col-lg-5 col-6">
            <button class="btn btn-dark w-100" type="button" data-toggle="collapse"
                    data-target="#employeeSearch" aria-expanded="false" aria-controls="employeeSearch">
                Знайти клієнта
            </button>
            <div class="collapse form-group" id="employeeSearch">
                <form method="get" action="/client">
                    <div class="form-row">
                        <div class="col-6">
                            <input type="text" class="form-control mr-1 mt-2" name="firstName"
                                   value="${RequestParameters.firstName!}" placeholder="Ім'я"/>
                        </div>
                        <div class="col">
                            <input type="text" class="form-control mr-1 mt-2" name="secondName"
                                   value="${RequestParameters.secondName!}" placeholder="Прізвище"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col">
                            <input type="date" class="form-control mr-1 mt-2" name="dateReg" min="2019-02-16"
                                   value="${RequestParameters.dateReg!}" placeholder="Дата реєстрації"/>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger ml-md-2 my-2 float-right">
                        <i class="fa fa-search"></i>
                    </button>
                </form>
            </div>
        </div>
    </div>

    <div class="row mt-5">
        <#if clients?has_content>
            <div class="h2">Список клієнтів:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col" class="text-danger">Id</th>
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
                        <th scope="row" class="text-danger">${client.id}</th>
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
            <div class="h3">Не знайдено жодного клієнта.</div>
        </#if>
    </div>
</@pad.page>