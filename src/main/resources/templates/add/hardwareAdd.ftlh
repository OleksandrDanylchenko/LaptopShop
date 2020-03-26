<#import "../parts/htmlPad.ftlh" as pad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../errorsHandlers/valueConsistentBreaker.ftlh" as vC>

<@pad.page "Hardware adding">
    <#if cpuModels?has_content>
        <#if ramModels?has_content>
            <#if ssdModels?has_content>
                <#if displayModels?has_content>
                    <#if hddModels?has_content>
                        <#if gpuModels?has_content>
                            <@eO.errorOutput errorMessage!/>

                            <p class="h1">Додати нову збірку:</p>
                            <form method="post" action="/hardware/add" class="form-group needs-validation w-100"
                                  novalidate onsubmit="return validateForm()">
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="assemblyName">Назва нової збірки:</label>
                                        <input type="text" class="form-control" name="assemblyName"
                                               value="${RequestParameters.assemblyName!}" required/>
                                        <div class="invalid-feedback">Назву нової збірки задано некоректно!</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="cpuModel">Модель процесору:</label>
                                        <select class="custom-select" name="cpuModel" required>
                                            <option value="" selected disabled></option>
                                            <#list cpuModels as cpuModel>
                                                <option>${cpuModel}</option>
                                            </#list>
                                        </select>
                                        <div class="invalid-feedback">Не обрано модель процесору!</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="gpuModel">Модель відеокарти:</label>
                                        <select class="custom-select" name="gpuModel" required>
                                            <option value="" selected disabled></option>
                                            <#list gpuModels as gpuModel>
                                                <option>${gpuModel}</option>
                                            </#list>
                                        </select>
                                        <div class="invalid-feedback">Не обрано модель відеокарти!</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="displayModel">Модель дисплею:</label>
                                        <select class="custom-select" name="displayModel" required>
                                            <option value="" selected disabled></option>
                                            <#list displayModels as displayModel>
                                                <option>${displayModel}</option>
                                            </#list>
                                        </select>
                                        <div class="invalid-feedback">Не обрано модель дисплею!</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="ramModel">Модель оперативної
                                            пам'яті:</label>
                                        <select class="custom-select" name="ramModel" required>
                                            <option value="" selected disabled></option>
                                            <#list ramModels as ramModel>
                                                <option>${ramModel}</option>
                                            </#list>
                                        </select>
                                        <div class="invalid-feedback">Не обрано модель оперативної пам'яті!</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="ssdModel">Модель SSD диску:</label>
                                        <select class="custom-select" name="ssdModel" required>
                                            <option value="" selected disabled></option>
                                            <#list ssdModels as ssdModel>
                                                <option>${ssdModel}</option>
                                            </#list>
                                        </select>
                                        <div class="invalid-feedback">Не обрано модель SSD диску!</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="ssdModel">Модель HDD диску:</label>
                                        <select class="custom-select" name="hddModel" required>
                                            <option value="" selected disabled></option>
                                            <#list hddModels as hddModel>
                                                <option>${hddModel}</option>
                                            </#list>
                                        </select>
                                        <div class="invalid-feedback">Не обрано модель HDD диску!</div>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-danger float-right">Додати</button>
                            </form>

                            <@eB.emptyBreaker inputIds=["assemblyName"]/>
                            <@vC.valueBreaker inputIds=["assemblyName"]/>
                        <#else>
                            <div class="alert alert-warning" role="alert">
                                <div class="h1 text-center">Не знайдено жодної моделі відеокарти.</div>
                                <div class="h3 text-center"><a href="/gpu/add">Додайте нову відеокарту</a>
                                    для створення збірки.
                                </div>
                            </div>
                        </#if>
                    <#else>
                        <div class="alert alert-warning" role="alert">
                            <div class="h1 text-center">Не знайдено жодної моделі HDD диску.</div>
                            <div class="h3 text-center"><a href="/hdd/add">Додайте новий HDD диск</a>
                                для створення збірки.
                            </div>
                        </div>
                    </#if>
                <#else>
                    <div class="alert alert-warning" role="alert">
                        <div class="h1 text-center">Не знайдено жодної моделі дисплею.</div>
                        <div class="h3 text-center"><a href="/display/add">Додайте новий дисплей</a>
                            для створення збірки.
                        </div>
                    </div>
                </#if>
            <#else>
                <div class="alert alert-warning" role="alert">
                    <div class="h1 text-center">Не знайдено жодної моделі SSD диску.</div>
                    <div class="h3 text-center"><a href="/ssd/add">Додайте новий SSD диск</a>
                        для створення збірки.
                    </div>
                </div>
            </#if>
        <#else>
            <div class="alert alert-warning" role="alert">
                <div class="h1 text-center">Не знайдено жодної моделі оперативної пам'яті.</div>
                <div class="h3 text-center"><a href="/ram/add">Додайте нову оперативну пам'ять</a>
                    для створення збірки.
                </div>
            </div>
        </#if>
    <#else>
        <div class="alert alert-warning" role="alert">
            <div class="h1 text-center">Не знайдено жодної моделі процесору.</div>
            <div class="h3 text-center"><a href="/cpu/add">Додайте новий процесор</a> для створення збірки.</div>
        </div>
    </#if>
</@pad.page>