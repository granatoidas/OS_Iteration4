
public class EntryPoint {
	static CPU cpu = new CPU();

	// sdfsdfsdfsdfs
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
									cpu.prasytiResurso("Pageidautina programa");
									this.segmentToRunFrom = 1;
									return;
								case 1:
									cpu.prasytiResurso("Kietasis diskas");
									this.segmentToRunFrom = 2;
									return;
								case 2:
									cpu.prasytiResurso("Supervizorine atmintis");
									this.segmentToRunFrom = 3;
									return;
								case 3:
									Resource r = cpu.kurtiResursa("Uzduotis supervizorineje atmintyje",
											resourcesInPossesion.get(0).data);
									cpu.atalaisvintiResursa(r);
									cpu.naikintiResursa(resourcesInPossesion.get(0));
									cpu.atalaisvintiResursa(resourcesInPossesion.get(0));
									cpu.atalaisvintiResursa(resourcesInPossesion.get(0));
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
									if (r.data.equals("SpausdinkLabas") || r.data.equals("Isjungti")) {
										Resource nr = cpu.kurtiResursa("MainProc Uzduotis", r.data);
										cpu.atalaisvintiResursa(nr);
									} else {
										Resource nr = cpu.kurtiResursa("Nera vartotojo programos", null);
										cpu.atalaisvintiResursa(nr);
									}
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
									cpu.prasytiResurso("LoaderJob");
									this.segmentToRunFrom = 1;
									return;
								case 1:
									cpu.prasytiResurso("Vartotojo atmintis");
									this.segmentToRunFrom = 2;
									return;
								case 2:
									Resource loaderJob = resourcesInPossesion.get(0);
									cpu.atalaisvintiResursa(resourcesInPossesion.get(1));
									cpu.naikintiResursa(loaderJob);
									cpu.atalaisvintiResursa(cpu.kurtiResursa("LoaderDone", loaderJob.parentId, null));
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
									Resource r = this.resourcesInPossesion.get(this.resourcesInPossesion.size() - 1);
									if (r.data.equals("deleteJobGovernor")) {
										cpu.naikintiProcesa(r.parent);
									} else {
										cpu.kurtiProcesa(new JobGovernor(Utils.getId(), this.id, r.data));
									}
									cpu.naikintiResursa(r);
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

					cpu.kurtiResursa("Kanalu irenginys", null);
					cpu.kurtiResursa("Supervizoriaus atmintis", null);
					for (int i = 0; i < 15; i++)
						cpu.kurtiResursa("Vartotojo atminties blokas", null);
					cpu.kurtiResursa("Kietasis diskas", null);
					this.segmentToRunFrom = 1;
					cpu.prasytiResurso("MOS pabaiga");
					return;
				case 1:
					System.exit(0);
				}
			}
		};
		Utils.LOG("Sukurtas procesas StartStop");
		cpu.pasiruose_procesai.add(startStop);
		cpu.planutojas();
	}

}

class JobGovernor extends Process {
	String data;
	CPU cpu = EntryPoint.cpu;

	JobGovernor(int id, int parentId, String data) {
		super(id, parentId, "JobGovernor");
		this.data = data;
	}

	void run() {
		switch (this.segmentToRunFrom) {
		case 0:
			cpu.prasytiResurso("Vartotojo atminties blokas");
			this.segmentToRunFrom = 1;
			return;
		case 1:
			cpu.atalaisvintiResursa(cpu.kurtiResursa("LoaderJob", null));
			cpu.prasytiResurso("LoaderDone");
			this.segmentToRunFrom = 2;
			return;
		case 2:
			cpu.naikintiResursa(this.resourcesInPossesion.get(1));
			cpu.kurtiProcesa(new VirtualMachine(Utils.getId(), this.id, this.data));

		}
	}

}

class VirtualMachine extends Process {
	String data;
	CPU cpu = EntryPoint.cpu;

	VirtualMachine(int id, int parentId, String data) {
		super(id, parentId, "JobGovernor");
		this.data = data;
	}

	void run() {
		switch (this.data) {
		case "SpausdinkLabas":

			break;
		case "Isjungti":

			break;
		}
	}

}
