import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { AccountService, JhiLanguageHelper } from 'app/core';
import { EChartOption } from 'echarts';
import { SidebarService } from '../../services/sidebar.service';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  error: string;
  success: string;
  settingsAccount: any;
  languages: any[];
  public visitorsOptions: EChartOption = {};
  public visitsOptions: EChartOption = {};
  public sidebarVisible: boolean = true;
  public activeTab: string = 'Overview';

  constructor(
    private accountService: AccountService,
    private languageService: JhiLanguageService,
    private languageHelper: JhiLanguageHelper,
    private sidebarService: SidebarService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.accountService.identity().then(account => {
      this.settingsAccount = this.copyAccount(account);
    });
    this.languageHelper.getAll().then(languages => {
      this.languages = languages;
    });
    this.visitorsOptions = this.loadLineChartOptions([3, 5, 1, 6, 5, 4, 8, 3], '#49c5b6');
    this.visitsOptions = this.loadLineChartOptions([4, 6, 3, 2, 5, 6, 5, 4], '#f4516c');
  }

  save() {
    this.accountService.save(this.settingsAccount).subscribe(
      () => {
        this.error = null;
        this.success = 'OK';
        this.accountService.identity(true).then(account => {
          this.settingsAccount = this.copyAccount(account);
        });
        this.languageService.getCurrent().then(current => {
          if (this.settingsAccount.langKey !== current) {
            this.languageService.changeLanguage(this.settingsAccount.langKey);
          }
        });
      },
      () => {
        this.success = null;
        this.error = 'ERROR';
      }
    );
  }

  copyAccount(account) {
    return {
      activated: account.activated,
      email: account.email,
      firstName: account.firstName,
      langKey: account.langKey,
      lastName: account.lastName,
      login: account.login,
      imageUrl: account.imageUrl
    };
  }

  toggleFullWidth() {
    this.sidebarService.toggle();
    this.sidebarVisible = this.sidebarService.getStatus();
    this.cdr.detectChanges();
  }

  toggleTabs(tab: string) {
    if (tab) {
      this.activeTab = tab;
    }
  }

  loadLineChartOptions(data, color) {
    let chartOption: EChartOption;
    let xAxisData: Array<any> = new Array<any>();

    data.forEach(element => {
      xAxisData.push('');
    });

    return (chartOption = {
      xAxis: {
        type: 'category',
        show: false,
        data: xAxisData,
        boundaryGap: false
      },
      yAxis: {
        type: 'value',
        show: false
      },
      tooltip: {
        trigger: 'axis',
        formatter: function(params, ticket, callback) {
          return (
            '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:' +
            color +
            ';"></span>' +
            params[0].value
          );
        }
      },
      grid: {
        left: '0%',
        right: '0%',
        bottom: '0%',
        top: '0%',
        containLabel: false
      },
      series: [
        {
          data: data,
          type: 'line',
          showSymbol: false,
          symbolSize: 1,
          lineStyle: {
            color: color,
            width: 1
          }
        }
      ]
    });
  }
}
