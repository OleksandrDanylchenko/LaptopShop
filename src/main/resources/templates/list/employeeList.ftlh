<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Employee">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-danger" href="/employee/add" role="button">
                <i class="far fa-plus-square"></i> Додати нового співробітника</a>
        </div>

        <div class="col-lg-5 col-6">
            <button class="btn btn-dark w-100" type="button" data-toggle="collapse"
                    data-target="#employeeSearch" aria-expanded="false" aria-controls="employeeSearch">
                Знайти співробітника
            </button>
            <div class="collapse form-group" id="employeeSearch">
                <form method="get" action="/employee">
                    <div class="form-row">
                        <div class="col">
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
                            <input type="text" class="form-control mr-1 mt-2" name="shopAddress"
                                   value="${RequestParameters.shopAddress!}" placeholder="Адреса магазину"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col">
                            <select class="custom-select mr-1 mt-2" name="isActive">
                                <option value="" selected disabled>Статус співробітника</option>
                                <option>Працюючий</option>
                                <option>Звільнений</option>
                            </select>
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
        <#if employees?has_content>
            <div class="h2">Список співробітників:</div>
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
                        <td>${employee.isActive?then("Працюючий", "Звільнений")}</td>
                        <td><a class="btn btn-outline-dark" href="/employee/edit/${employee.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-danger" href="/employee/delete/${employee.id}"
                               role="button" onclick="return confirm('Ви впевнені?')"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного співробітника.</div>
        </#if>
    </div>
</@pad.page>