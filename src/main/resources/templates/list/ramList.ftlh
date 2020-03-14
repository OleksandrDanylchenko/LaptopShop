<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "RAM">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-danger" href="/ram/add" role="button">
                <i class="far fa-plus-square"></i> Додати нову оперативну пам'ять</a>
        </div>
        <div class="d-flex justify-content-center">
            <a class="btn btn-success mr-2" id="fixedBtnHeight" href="/ram/importExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i></a>
            <a class="btn btn-success" id="fixedBtnHeight" href="/ram/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#ramSearch" aria-expanded="false" aria-controls="ramSearch">
                Знайти оперативну пам'ять
            </button>
            <div class="collapse form-group" id="ramSearch">
                <form method="get" action="/ram">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="model"
                                   value="${RequestParameters.model!}" placeholder="Модель"/>
                        </div>
                        <div class="col-4">
                            <input type="number" min="1" class="form-control" name="memory"
                                   value="${RequestParameters.memory!}" placeholder="Пам'ять(GiB)"/>
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
        <#if rams?has_content>
            <div class="h1">Список оперативної пам'яті:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Id</th>
                    <th scope="col">Модель</th>
                    <th scope="col">Пам'ять(GiB)</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list rams as ram>
                    <tr>
                        <th class="text-danger" scope="row">${ram.id}</th>
                        <td>${ram.model}</td>
                        <td>${ram.memory}</td>
                        <td><a class="btn btn-outline-dark" href="/ram/edit/${ram.id}"
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