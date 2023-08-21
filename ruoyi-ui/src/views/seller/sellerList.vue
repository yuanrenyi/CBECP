<template>
  <div class="app-container flex flex-column" id="sellerList">
    <el-form  class="query" :model="queryParams" ref="queryForm" size="small" v-show="showSearch" label-width="88px">
      <el-row :gutter="15">
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="企业名称" prop="compName">
            <el-input
              v-model="queryParams.compName"
              placeholder="请输入企业名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="项目负责人" prop="pmName">
            <el-input
              v-model="queryParams.pmName"
              placeholder="请输入项目负责人"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="负责人电话" prop="pmPhone">
            <el-input
              v-model="queryParams.pmPhone"
              placeholder="请输入负责人电话"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="微信群" prop="wechatGroup">
            <el-input
              v-model="queryParams.wechatGroup"
              placeholder="请输入微信群"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="是否已开会议" prop="ifMeeting">
            <el-select v-model="queryParams.ifMeeting" placeholder="请选择是否已开会议" clearable>
              <el-option
                v-for="dict in dict.type.seller_has_metting"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="培训人" prop="trainer">
            <el-input
              v-model="queryParams.trainer"
              placeholder="请输入培训人"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="是否有微信小程序" prop="ifWechatMiniapp">
            <el-select v-model="queryParams.ifWechatMiniapp" placeholder="请选择是否有微信小程序" clearable>
              <el-option
                v-for="dict in dict.type.seller_has_miniprogram"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8">
          <el-form-item label="合作模式" prop="cooperationMode">
            <el-select v-model="queryParams.cooperationMode" placeholder="请选择合作模式" clearable>
              <el-option
                v-for="dict in dict.type.seller_cooperation_mode"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" v-if="agentControl" >
          <el-form-item label="是否为代理商" prop="ifAgent">
            <el-select v-model="queryParams.ifAgent" placeholder="请选择是否为代理商" clearable>
              <el-option
                v-for="dict in dict.type.common_yes_no_normal"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4.8" v-if="agentControl">
          <el-form-item label="代理商" prop="parentAgentId">
            <select-table
              v-model="compIdQuery"
              placeholder="请选择代理商"
              ref="agentQuery"
              :table="sellerTable"
              :columns="sellerTable.columns"
              :keywords="{label:'compName',value:'id'}"
              @page-change="agentPageChange"
              @clear="resetQueryAgent"
              @radioChange="agentQueryChange"
              :max-height="500"
              @inputKeyword="filterAgent"
              @focus="getAgentList"
              radioTxt="#"
              isShowPagination
            ></select-table>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <div class="h-40-middle">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </div>
        </el-col>
      </el-row>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5" v-if="showButtonFlag">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['seller:info:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['seller:info:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['seller:info:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['seller:info:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      class="flex-1"
      header-cell-class-name="table-header-cell"
      border stripe
      v-loading="loading"
      :data="sellerList"
      height="99%"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="销售方ID" align="center" prop="id" />-->
      <el-table-column label="企业名称" align="center" prop="compName"/>
      <el-table-column label="项目负责人" align="center" prop="pmName" width="110"/>
      <el-table-column label="负责人电话" align="center" prop="pmPhone" width="120"/>
      <el-table-column label="微信群" align="center" prop="wechatGroup" width="120"/>
      <el-table-column label="省" align="center" prop="compProvinceName"/>
      <el-table-column label="市" align="center" prop="compCityName"/>
      <el-table-column label="企业规模" align="center" show-overflow-tooltip prop="compScale" width="100"/>
      <el-table-column label="是否已开会议" align="center" prop="ifMeeting" width="80">
        <template #header="{column, $index}">
          <!--          <span v-showPoper="{content: '是否已开会议', y: 25}">是否已开会议</span>-->
          <el-tooltip
            content="是否已开会议"
            :offset="tooltip.ifMeetingTableTipOffsetX"
            :arrowOffset="tooltip.ifMeetingTableTipArrowOffsetX"
            ref="ifMeetingTableTip"
            :disabled="tooltip.ifMeetingTableTip"
            placement="top-start">
            <span class="ellipse-text">是否已开会议</span>
          </el-tooltip>
        </template>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.seller_has_metting" :value="scope.row.ifMeeting"/>
        </template>
      </el-table-column>
      <el-table-column label="会议时间" align="center" prop="meetingTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.meetingTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="培训人" align="center" prop="trainer"/>
      <el-table-column label="人员数量" align="center" prop="trainNum"/>
      <el-table-column label="是否有微信小程序" align="center" prop="ifWechatMiniapp" width="80">
        <template #header="{column, $index}">
          <el-tooltip
            content="是否有微信小程序"
            :offset="tooltip.ifWechatMiniappTableTipOffsetX"
            :arrowOffset="tooltip.ifWechatMiniappTableTipArrowOffsetX"
            ref="ifWechatMiniappTableTip"
            :disabled="tooltip.ifWechatMiniappTableTip"
            placement="top-start">
            <span class="ellipse-text">是否有微信小程序</span>
          </el-tooltip>
        </template>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.seller_has_miniprogram" :value="scope.row.ifWechatMiniapp"/>
        </template>
      </el-table-column>
      <el-table-column label="合作模式" align="center" prop="cooperationMode">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.seller_cooperation_mode" :value="scope.row.cooperationMode"/>
        </template>
      </el-table-column>
      <el-table-column
        label="会议跟进情况"
        show-overflow-tooltip
        align="center"
        prop="meetingCondition"
        width="120"/>
      <el-table-column label="合同进度" show-overflow-tooltip align="center" prop="contractProgress"/>
      <el-table-column label="款项时间" align="center" prop="paymentTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.paymentTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="是否为代理商" align="center" prop="ifAgent" width="80" v-if="agentControl">
        <template #header="{column, $index}">
          <!--          <span v-showPoper="{content: '是否已开会议', y: 25}">是否已开会议</span>-->
          <el-tooltip
            content="是否为代理商"
            :offset="tooltip.ifAgentTableTipOffsetX"
            :arrowOffset="tooltip.ifAgentTableTipArrowOffsetX"
            ref="ifAgentTableTip"
            :disabled="tooltip.ifAgentTableTip"
            placement="top-start">
            <span class="ellipse-text">是否为代理商</span>
          </el-tooltip>
        </template>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.common_yes_no_normal" :value="scope.row.ifAgent"/>
        </template>
      </el-table-column>
      <el-table-column label="所属代理商" align="center" prop="parentAgentName" v-if="agentControl"/>
      <el-table-column label="备注" align="center" show-overflow-tooltip prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="120">
        <template slot-scope="scope">
          <div class="flex flex-justify-center">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-date"
              @click="handleFollowUp(scope.row)"
            >跟进情况
            </el-button>
          </div>
          <div class="flex flex-justify-around">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['seller:info:edit']"
            >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['seller:info:delete']"
            >删除
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改销售方信息对话框 -->
    <a-modal
      :title="title" :visible.sync="open"
      width="72%" :destroyOnClose="true" append-to-body
      :zIndex="2001"
      @cancel="open = false">
      <el-form ref="form" class="edit" :model="form" :rules="rules" label-width="78px">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="24" :lg="16" :xl="16">
            <el-form-item label="企业名称" prop="compName">
              <el-input v-model="form.compName" placeholder="请输入企业名称"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="项目负责人" prop="pmName">
              <el-input v-model="form.pmName" placeholder="请输入项目负责人"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="负责人电话" prop="pmPhone">
              <el-input v-model="form.pmPhone" placeholder="请输入负责人电话"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="微信群" prop="wechatGroup">
              <el-input v-model="form.wechatGroup" placeholder="请输入微信群"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="省" prop="compProvince">
              <el-select
                ref="pro"
                @focus="focusSelect('pro')"
                @change="changePro($event, form,'compProvince','compProvinceName')"
                @clear="clearPro(form,['compProvince','compProvinceCity'],['compProvinceName','compCityName'])"
                clearable
                value-key="provinceID"
                v-model="provinceObj"
                placeholder="请选择省/直辖市">
                <el-option
                  v-for="item in provinceList"
                  :key="item.provinceID"
                  :label="item.province"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="市" prop="compCity">
              <el-select
                clearable
                @focus="focusSelect('city')"
                ref="city"
                @change="changeCity($event, form,'compCity','compCityName')"
                @clear="clearCity(form, ['compProvinceName'],['compCityName'])"
                value-key="cityID"
                v-model="cityObj"
                placeholder="请先选择市">
                <el-option
                  v-for="item in cityList"
                  :key="item.cityID"
                  :label="item.city"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="企业规模" prop="compScale">
              <el-input v-model="form.compScale" placeholder="请输入企业规模"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="是否已开会议" prop="ifMeeting">
              <el-select @focus="focusSelect('ifMeetingSelect')" ref="ifMeetingSelect" v-model="form.ifMeeting"
                         placeholder="请选择是否已开会议" clearable>
                <el-option
                  v-for="dict in dict.type.seller_has_metting"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="会议时间" prop="meetingTime">
              <el-date-picker clearable
                              v-model="form.meetingTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择会议时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="培训人" prop="trainer">
              <el-input v-model="form.trainer" placeholder="请输入培训人"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="人员数量" prop="trainNum">
              <el-input v-model="form.trainNum" placeholder="请输入人员数量"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="是否有微信小程序" prop="ifWechatMiniapp">
              <el-select ref="ifWechatMiniappSelect" @focus="focusSelect('ifWechatMiniappSelect')"
                         v-model="form.ifWechatMiniapp" placeholder="请选择是否有微信小程序" clearable>
                <el-option
                  v-for="dict in dict.type.seller_has_miniprogram"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="合作模式" prop="cooperationMode">
              <el-select @focus="focusSelect('cooperationMode')" ref="cooperationMode" v-model="form.cooperationMode"
                         placeholder="请选择合作模式" clearable>
                <el-option
                  v-for="dict in dict.type.seller_cooperation_mode"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="合同进度" prop="contractProgress">
              <el-input v-model="form.contractProgress" placeholder="请输入合同进度"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="款项时间" prop="paymentTime">
              <el-date-picker clearable
                              v-model="form.paymentTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择款项时间">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="用户编码" prop="userCode">
              <el-input v-model="form.userCode" placeholder="请输入合同进度"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="海关备案名称" prop="customsCode">
              <el-input v-model="form.customsCode" placeholder="海关备案名称"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="渠道编码" prop="enterpriseName">
              <el-input v-model="form.cnCode" placeholder="渠道编码"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="179公告对接地址" prop="encryptUrl">
              <el-input v-model="form.encryptUrl" placeholder="179公告对接地址"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="系统ID" prop="msid">
              <el-input v-model="form.msid" placeholder="MallSystem表ID"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="微信二级子商户号" prop="wxmchid">
              <el-input v-model="form.wxmchid" placeholder="微信二级子商户号"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
            <el-form-item label="微信清关关区编码" prop="customName">
              <el-input v-model="form.customName" placeholder="微信清关关区编码"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8"  v-if="agentControl">
            <el-form-item label="是否为代理商" prop="ifAgent">
              <el-select @focus="focusSelect('ifAgent')" ref="ifAgent" v-model="form.ifAgent"
                         placeholder="请选择是否为代理商" clearable>
                <el-option
                  v-for="dict in dict.type.common_yes_no_normal"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8"  v-if="agentControl">
            <el-form-item label="所属代理商" prop="parentAgentId">
              <select-table
                v-model="compIdQuery1"
                placeholder="请选择所属代理商"
                ref="agentQuery1"
                :table="sellerTable1"
                :columns="sellerTable1.columns"
                :keywords="{label:'compName',value:'id'}"
                @page-change="agentPageChange1"
                @clear="resetQueryAgent1"
                @radioChange="agentQueryChange1"
                :max-height="500"
                @inputKeyword="filterAgent1"
                @focus="getAgentList1"
                radioTxt="#"
                isShowPagination
              ></select-table>
            </el-form-item>
          </el-col>


          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8"  v-if="agentControl">
            <el-form-item label="价格比例（%）" prop="proportion">
              <el-input v-model="form.proportion" placeholder="请输入价格比例"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="会议跟进情况" prop="meetingCondition">
              <el-input type="textarea" :autosize="{minRows: 2, maxRows: 3}" v-model="form.meetingCondition"
                        placeholder="请输入会议跟进情况"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" :autosize="{minRows: 2, maxRows: 3}" v-model="form.remark"
                        placeholder="请输入备注"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </a-modal>

    <!--    跟进历史记录-->
    <a-modal
      :title="title"
      :visible.sync="openFollowUp"
      width="60%"
      :zIndex="1001"
      :destroyOnClose="true"
      append-to-body @cancel="openFollowUp = false">
      <el-form
        :model="followUpForm"
        class="edit"
        ref="followform"
        label-width="90px"
        :rules="followUpRules">
        <el-row>
          <el-col :span="12">
            <el-form-item label="跟进时间" prop="followupTime">
              <el-date-picker clearable
                              v-model="followUpForm.followupTime"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              default-time="09:00:00"
                              placeholder="请选择跟进时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label-width="123px" label="更新跟进节点" prop="followupItem">
              <el-select
                @focus="focusSelect('followupItem')"
                ref="followupItem"
                v-model="followUpForm.followupItem" placeholder="选择进度节点" clearable>
                <el-option
                  v-for="dict in dict.type.seller_follow_node"
                  :key="dict.label"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="跟进说明" prop="followupDesc">
              <el-input
                type="textarea"
                :autosize="{minRows: 2, maxRows: 3}"
                v-model="followUpForm.followupDesc"
                placeholder="请输入会议跟进情况"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <div class="flex flex-justify-end mr20">
              <el-button type="primary" size="mini" @click="submitFollowForm">提 交</el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>

      <el-divider content-position="center">跟进历史记录</el-divider>
      <el-table
        :data="followUpList"
        border
      >
        <el-table-column label="跟进时间" width="175" prop="followupTime" align="center">

        </el-table-column>
        <el-table-column label="跟进节点" width="120" prop="followupItem" align="center">
        </el-table-column>
        <el-table-column label="跟进人" width="120" prop="followupName" align="center">
        </el-table-column>
        <el-table-column label="跟进情况说明" prop="followupDesc" show-overflow-tooltip align="center">
        </el-table-column>
      </el-table>

      <div slot="footer" class="dialog-footer">
      </div>
    </a-modal>

  </div>
</template>

<script>
import {
  listSeller,
  getSeller,
  delSeller,
  addSeller,
  updateSeller,
  followupList,
  addFollowup,
  showAddButtonFlag,
  getUserType,
} from "@/api/seller/seller";
import {checkOverflow, focusSelect, isEllipsis} from '@/utils/ruoyi'
import {checkPermi, checkRole, uncheckRole} from "@/utils/permission";
import {clone} from 'lodash'
import areaMix from "@/mixins/areaMix";
import agentMix from "@/mixins/agentMix";
import agentMix1 from "@/mixins/agentMix1";
import storeHouseMix from "@/mixins/storeHouseMix";
import supplierMix from "@/mixins/supplierMix";

export default {
  name: "Seller",
  mixins: [areaMix, agentMix, agentMix1],
  dicts: ['seller_has_miniprogram', 'seller_has_metting','seller_cooperation_mode','seller_follow_node','common_yes_no_normal'],
  data() {
    let vueInctance = this
    return {
      vueInctance,
      slctParentAgent: undefined,
      compIdQuery1: null,
      tooltip: {
        ifWechatMiniappTableTip: true,
        ifWechatMiniappTableOffsetX: 0,
        ifWechatMiniappTableArrowOffsetX: 35,

        ifMeetingTableTip: true,
        ifMeetingTableTipOffsetX: 0,
        ifWechatMiniappTableTipArrowOffsetX: 35,

        ifAgentTableTip: true,
        ifAgentTableTipOffsetX: 0,
        ifAgentMiniappTableTipArrowOffsetX: 35,
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      resizeObserver: null,
      // 总条数
      total: 0,
      // 销售方信息表格数据
      sellerList: [],
      showButtonFlag: false,
      agentControl: false,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openFollowUp: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compName: null,
        pmName: null,
        pmPhone: null,
        wechatGroup: null,
        compProvince: null,
        compCity: null,
        compScale: null,
        ifMeeting: null,
        meetingTime: null,
        trainer: null,
        trainNum: null,
        ifWechatMiniapp: null,
        cooperationMode: null,
        meetingCondition: null,
        contractProgress: null,
        paymentTime: null,
        ifAgent: null,
        parentAgentId: null,
        proportion: null,
        slctParentAgent: null,
      },
      // 跟进了列表
      followUpList: [],
      followUpForm: {
        followupItem: '',// 跟进节点
        followupTime: '',// 跟进时间
        followupDesc: '',// 跟进说明
        sellerId: '',// 销售方id
      },
      /*节点角色1*/
      followupItem1: [
        {followupItem: '合同跟进',}
      ],
      /*节点角色2*/
      followupItem2: [
        {followupItem2: '办理资质',}
      ],
      /*节点角色3*/
      followupItem3: [
        {followupItem: '小程序申请'},
        {followupItem: '支付进件'},
        {followupItem: '海关备案'},
        {followupItem: '其他'},
      ],
      followUpRules: {
        followupTime: [
          {required: true, message: "请选择跟进时间", trigger: "blur"}
        ],
        followupItem: [
          {required: true, message: "请选择跟进节点", trigger: "blur"}
        ],
        followupDesc: [
          {required: true, message: "请填写跟进说明", trigger: "blur"}
        ],
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        compName: [
          {required: true, message: "企业名称不能为空", trigger: "change"}
        ],
        pmName: [
          {required: true, message: "项目负责人不能为空", trigger: "change"}
        ],
        pmPhone: [
          {required: true, message: "负责人电话不能为空", trigger: "change"}
        ],
        compProvince: [
          {required: true, message: "请选择省", trigger: "change"}
        ],
        compCity: [
          {required: true, message: "请选择市", trigger: "change"}
        ],
        compScale: [
          {required: true, message: "企业规模不能为空", trigger: "change"}
        ],
        cooperationMode: [
          {required: true, message: "请选择合作模式", trigger: "change"}
        ],
        ifAgent: [
          {required: true, message: "请选择是否为代理商", trigger: "change"}
        ],
        parentAgentId: [
          {required: true, message: "请选择所属代理商", trigger: "change"}
        ],
        meetingTime: [
          // {required: true, message: "会议时间不能为空", trigger: "blur"}
        ],
        paymentTime: [
          // {required: true, message: "款项时间不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.showButton();
    this.showAgentControl();
  },
  mounted() {
    this.$nextTick(() => {
      this.resizeObserver = new ResizeObserver((entries) => {
        entries.forEach((item, index) => {
          item.target
          this.checkOverflow(item.target)
          // console.log(item.contentRect)
        })
      })
      for (const key in this.$refs) {
        if (key.includes('Tip')) {
          this.$refs[key].$el.parentNode.__refName = key
          this.resizeObserver.observe(this.$refs[key].$el.parentNode)
        }
      }
    })
  },
  destroyed() {
    this.resizeObserver = null
  },
  methods: {
    checkOverflow,
    /** 查询销售方信息列表 */
    getList() {
      this.loading = true;
      listSeller(this.queryParams).then(response => {
        this.sellerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    checkRole,
    uncheckRole,
    showButton() {
      showAddButtonFlag().then(response => {
        console.log('Seller->showButtonFlag')
        console.log(response.data)
        if(response.data == 1) {
          this.showButtonFlag = true;
        }
      });
    },

    showAgentControl() {
      getUserType().then(response => {
        if(response.data == 1) {
          this.agentControl = true;
        }
      })
    },

    // 选择代理商
    agentQueryChange(val) {
      this.queryParams.parentAgentId = val && val.id ? val.id : undefined
      this.compIdQuery = val && val.id ? val.id : undefined
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
      this.resetQueryAgent();
    },

    resetQueryAgent() {
      console.log('vue --- clear')
      this.queryParams.parentAgentId=''
      this.compIdQuery = ''
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      // this.$forceUpdate()
      this.title = "添加销售方信息";
    },
    /*更新进度*/
    handleFollowUp(row) {
      // 跟进详情列表
      this.title = '跟进详情'
      const {id: sellerId} = row
      followupList({sellerId}).then(res => {
        this.followUpList = res.data
        this.followUpForm.sellerId = row.id
        this.openFollowUp = true
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.resetAreaList()
      const id = row.id || this.ids
      getSeller(id).then(response => {
        this.form = response.data;
        let provinceID = clone(this.form.compProvince)
        let province = clone(this.form.compProvinceName)
        let proObj = {provinceID, province}
        let cityID = clone(this.form.compCity)
        let city = clone(this.form.compCityName)
        let cityObj = {cityID, city}
        this.open = true;
        this.initSelectArea(proObj, cityObj)

        this.resetFormAgent1();
        let prntAgentId = clone(this.form.parentAgentId)
        this.slctParentAgent = prntAgentId

        this.compIdQuery1 = null,
        this.initAgentShow1(prntAgentId)
        this.title = "修改销售方信息";
      });
    },
    /* 更新 节点进度*/
    submitFollowForm() {
      this.$refs["followform"].validate(valid => {
        if(!valid) return
        this.$modal.confirm('确认更新跟进节点?').then(() => {
          addFollowup(this.followUpForm).then(res => {
            this.$modal.msgSuccess('更新成功')
            this.openFollowUp = false
            this.resetFollowForm()
          })
        }).catch(() => {
        })
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          delete  this.form.servicePlan
          if (this.form.id != null) {
            updateSeller(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSeller(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      let delTitle
      delTitle = row && row.id ? '是否确认删除销售方名称为"' + row.compName + '"的数据项？' : '是否确认删除所选销售方？'
      this.$modal.confirm(delTitle).then(function () {
        return delSeller(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/seller/export', {
        ...this.queryParams
      }, `seller_${new Date().getTime()}.xlsx`)
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compName: null,
        pmName: null,
        pmPhone: null,
        wechatGroup: null,
        compProvince: null,
        compProvinceName: null,
        compCity: null,
        compCityName: null,
        compScale: null,
        ifMeeting: null,
        meetingTime: null,
        trainer: null,
        trainNum: null,
        ifWechatMiniapp: null,
        cooperationMode: null,
        meetingCondition: null,
        contractProgress: null,
        paymentTime: null,
        remark: null,
        userCode: null,
        enterpriseName: null,
        customsCode: null,
        cnCode: null,
        encryptUrl: null,
        msid: null,
        wxmchid: null,
        customName: null,
      };
      this.resetForm("form");
    },
    resetFollowForm () {
      this.followUpForm = {
        followupItem: '',// 跟进节点
        followupTime: '',// 跟进时间
        followupDesc: '',// 跟进说明
        sellerId: '',// 销售方id
      }
      this.resetForm("followform");
    },


    initAgentShow1(currentAgentId) {
      this.$nextTick(()=>{
        setTimeout(()=>{
          // this.$refs.storeHouseForm.searchKey = this.form.storehouseName
          this.compIdQuery1 = currentAgentId
        },100)
        // this.$refs.storeHouseForm.defaultValue.storehouseName = this.form.storehouseName
      })
    },

  },

};
</script>

<style lang="scss" scoped>

.query .el-form-item {
  margin-right: 8px;
}
</style>
