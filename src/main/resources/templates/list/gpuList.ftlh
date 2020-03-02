<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "GPU">
    <div class="row">
        <div class="col">
            <a class="btn btn-outline-danger float-left" href="/gpu/add" role="button">
                <i class="far fa-plus-square"></i> Додати нову відеокарту</a>
        </div>
        <div class="col-1 mr-2 mr-sm-0">
            <a class="btn btn-success" href="/gpu/exportExcel" role="button"><i class="fas fa-file-excel"></i></a>
        </div>
        <div class="col-lg-4 col-5">
            <button class="btn btn-dark w-100" type="button" data-toggle="collapse"
                    data-target="#gpuSearch" aria-expanded="false" aria-controls="gpuSearch">
                Знайти відеокарту
            </button>
            <div class="collapse form-group" id="gpuSearch">
                <form method="get" action="/gpu">
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
        <#if gpus?has_content>
            <div class="h1">Список процесорів:</div>
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
                <#list gpus as gpu>
                    <tr>
                        <th class="text-danger" scope="row">${gpu.id}</th>
                        <td>${gpu.model}</td>
                        <td>${gpu.memory}</td>
                        <td><a class="btn btn-outline-dark" href="/gpu/edit/${gpu.id}"
                               role="button"><i class="fa fa-edit"></i></a></td>
                        <td><a class="btn btn-outline-danger" href="/gpu/delete/${gpu.id}"
                               role="button" onclick="return confirm('Ви впевнені?')"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не знайдено жодної відеокарти.</div>
        </#if>
    </div>
</@pad.page>