<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>

<@htmlPad.page "User table">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#userAddModal">
                <i class="far fa-plus-square"></i> Додати нового користувача
            </button>
            <@addModal.userAdd/>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#userSearch" aria-expanded="false" aria-controls="userSearch">
                Знайти користувача
            </button>
            <div class="collapse form-group" id="userSearch">
                <form method="get" action="/user">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="username"
                                   value="${RequestParameters.username!}" placeholder="Логін"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <select class="custom-select" name="isActive">
                                <option value="" selected disabled>Статус користувача</option>
                                <option>Активний</option>
                                <option>Неактивний</option>
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
        <div class="row">
            <div class="col">
                <@eO.errorOutput errorMessage!/>
            </div>
        </div>
        <#if users?has_content>
            <div class="h1">Список користувачів:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Id</th>
                    <th scope="col">Логін</th>
                    <th scope="col">Роль</th>
                    <th scope="col">Статус користувача</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list users as user>
                    <tr>
                        <th class="text-danger" scope="row">${user.id}</th>
                        <td>${user.username}</td>
                        <td>${user.role}</td>
                        <td>${user.isActive?then("Активний", "Неактивний")}</td>
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#userEditModal${user.id}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.userEdit id="${user.id}" currentUsername="${user.username}"
                            currentPassword="${user.password}" currentRole="${user.role}"
                            activeStatus="${user.isActive?string('Активний', 'Неактивний')}"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${user.id}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${user.id}"
                            deletionLink="/user/delete/${user.id}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодного користувача.</div>
        </#if>
    </div>
</@htmlPad.page>