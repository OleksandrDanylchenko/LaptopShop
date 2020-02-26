<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "RAM">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-primary " href="/ram/add" role="button">
                <i class="far fa-plus-square"></i> Додати нову оперативну пам'ять</a>
        </div>

        <div class="col-lg-3 col-4">
            <button class="btn btn-primary w-100" type="button" data-toggle="collapse"
                    data-target="#ramSearch" aria-expanded="false" aria-controls="ramSearch">
                Знайти оперативну пам'ять
            </button>
            <div class="collapse form-group" id="ramSearch">
                <form method="get" action="/ram">
                    <input type="text" class="form-control mr-1 mt-2" name="model"
                           value="${RequestParameters.model!}" placeholder="Модель"/>
                    <input type="number" min="1" class="form-control mr-1 mt-2" name="memory"
                           value="${RequestParameters.memory!}" placeholder="Пам'ять(GiB)"/>
                    <button type="submit" class="btn btn-primary ml-md-2 my-2 float-right">
                        <i class="fa fa-search"></i>
                    </button>
                </form>
            </div>
        </div>
    </div>

    <div class="row mt-5">
        <#if rams?has_content>
            <div class="h2">Список оперативної пам'яті:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Модель</th>
                    <th scope="col">Пам'ять(GiB)</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list rams as ram>
                    <tr>
                        <th scope="row">${ram.id}</th>
                        <td>${ram.model}</td>
                        <td>${ram.memory}</td>
                        <td><a class="btn btn-outline-primary" href="/ram/edit/${ram.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-danger" href="/ram/delete/${ram.id}"
                               role="button" onclick="return confirm('Ви впевнені?')"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодної оперативної пам'яті.</div>
        </#if>
    </div>
</@pad.page>