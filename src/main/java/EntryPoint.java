
public class EntryPoint {
	static CPU cpu = new CPU();

	public static void main(String[] args) {
		Process startStop = new Process(0, 0, "StarStop") {
			void run() {
				switch (this.segmentToRunFrom) {
				case 0:
					cpu.kurtiProcesa(new Process(Utils.getId(), this.id, "ReadFromHDD") {
						void run() {
							while (true) {
								switch (this.segmentToRunFrom) {
								case 0:
									cpu.prasytiResurso("Kietasis diskas");
									this.segmentToRunFrom = 1;
									return;
								case 1:
									cpu.prasytiResurso("Supervizorine atmintis");
									this.segmentToRunFrom = 2;
									return;
								case 2:
									cpu.kurtiResursa("Uzduotis supervizorineje atmintyje");
								}
								this.segmentToRunFrom = 0;
							}
						}
					});
					cpu.kurtiProcesa(new Process(Utils.getId(), this.id, "JCL") {
						void run() {
							while (true) {
								switch (this.segmentToRunFrom) {
								case 0:
									cpu.prasytiResurso("Uzduotis supervizorineje atmintyje");
									this.segmentToRunFrom = 1;
									return;
								case 1:
									UserProcessResource r = (UserProcessResource) this.resourcesInPossesion
											.get(this.resourcesInPossesion.size() - 1);
									cpu.naikintiResursa(r);
									//cpu.kur
									this.segmentToRunFrom = 2;
									return;
								case 2:
									;
								}
								this.segmentToRunFrom = 0;
							}
						}
					});
					cpu.kurtiProcesa(new Process(Utils.getId(), this.id, "Loader") {
						void run() {
							while (true) {
								switch (this.segmentToRunFrom) {
								case 0:
									;
								case 1:
									;
								case 2:
									;
								}
								this.segmentToRunFrom = 0;
							}
						}
					});
					cpu.kurtiProcesa(new Process(Utils.getId(), this.id, "MainProc") {
						void run() {
							while (true) {
								switch (this.segmentToRunFrom) {
								case 0:
									cpu.prasytiResurso("MainProc uzduotis");
									this.segmentToRunFrom = 1;
									return;
								case 1:
									UserProcessResource r = (UserProcessResource) this.resourcesInPossesion
											.get(this.resourcesInPossesion.size() - 1);
									if (r.status == 2){
										cpu.naikintiProcesa(r.parent);
										cpu.naikintiResursa(r);
									} else{
										
									}
									this.segmentToRunFrom = 2;
									return;
								case 2:
									;
								}
								this.segmentToRunFrom = 0;
							}
						}
					});
					cpu.kurtiProcesa(new Process(Utils.getId(), this.id, "Interrupt") {
						void run() {
							while (true) {
								switch (this.segmentToRunFrom) {
								case 0:
									cpu.prasytiResurso("MainProc uzduotis");
									this.segmentToRunFrom = 1;
									return;
								case 1:
									cpu.prasytiResurso(name);
								case 2:
									;
								}
								this.segmentToRunFrom = 0;
							}
						}
					});
					cpu.kurtiProcesa(new Process(Utils.getId(), this.id, "PrintLine") {
						void run() {
							while (true) {
								switch (this.segmentToRunFrom) {
								case 0:
									;
								case 1:
									;
								case 2:
									;
								}
								this.segmentToRunFrom = 0;
							}
						}
					});
					cpu.kurtiProcesa(new Process(Utils.getId(), this.id, "ReadFromUser") {
						void run() {
							while (true) {
								switch (this.segmentToRunFrom) {
								case 0:
									;
								case 1:
									;
								case 2:
									;
								}
								this.segmentToRunFrom = 0;
							}
						}
					});

					cpu.kurtiResursa("Kanalu irenginys");
					cpu.kurtiResursa("Supervizoriaus atmintis");
					for (int i = 0; i < 15; i++)
						cpu.kurtiResursa("Vartotojo atminties blokas");
					cpu.kurtiResursa("Kietasis diskas");
					this.segmentToRunFrom = 1;
					cpu.prasytiResurso("MOS pabaiga");
					return;
				case 1:
					System.exit(0);
				}
			}
		};
		cpu.pasiruose_procesai.add(startStop);
		cpu.planutojas();
	}

}
