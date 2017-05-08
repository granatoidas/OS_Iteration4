import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CPU {
	ArrayList<Process> pasiruose_procesai = new ArrayList<Process>();
	ArrayList<Process> sustabdyti_procesai = new ArrayList<Process>();
	Process aktyvus_procesas = null;

	ArrayList<Resource> laisvi_resursai = new ArrayList<Resource>();
	ArrayList<Resource> uzimti_resursai = new ArrayList<Resource>();

	LinkedHashMap<Process, String> blokuoti_procesai = new LinkedHashMap<Process, String>();

	// planuotojas ir jo funkcijos
	void planutojas() {
		while (true) {
			Utils.LOG("Pradetas planuotojas");
			Process a = getReadyProcess();
			for (Process p : pasiruose_procesai) {
				if (p.priority > a.priority)
					a = p;
			}
			for (Process p : pasiruose_procesai) {
				if (!a.equals(p))
					p.increasePriority();
			}
			a.decreasePriority();
			this.aktyvus_procesas = a;
			Utils.LOG("Pradetas vykdyti procesas " + a.name + " prioritetas: " + a.priority);
			a.run();
		}
	}

	Process getReadyProcess() {
		Process a = null;
		if (pasiruose_procesai.isEmpty()) {
			skirstytojas();
		}
		if (pasiruose_procesai.isEmpty()) {
			a = getReadyProcess();
			return a;
		}
		a = pasiruose_procesai.get(0);
		return a;
	}

	// skirstytojas ir jo funkcijos
	void skirstytojas() {
		Utils.LOG("Pradetas vykdyti skirstytojas");
		for (Process p : blokuoti_procesai.keySet()) {
			Resource a = null;
			for (Resource r : laisvi_resursai) {
				if (r.adresatoId != null) {
					if (r.adresatoId == p.id && blokuoti_procesai.get(p).equals(r.name)) {
						a = r;
						break;
					}
					continue;
				}
				if (blokuoti_procesai.get(p).equals(r.name)) {
					a = r;
					break;
				}
			}
			if (a == null)
				continue;
			laisvi_resursai.remove(a);
			uzimti_resursai.add(a);
			p.resourcesInPossesion.add(a);
			blokuoti_procesai.remove(p);
			pasiruose_procesai.add(p);
			Utils.LOG("Procesui " + p.name + " atiduotas resursas " + a.name);
		}
	}

	// procesu primityvai
	void kurtiProcesa(Process p) {
		pasiruose_procesai.add(p);
		this.aktyvus_procesas.children.add(p);
		Utils.LOG("Sukurtas procesas " + p.name);
	}

	void naikintiProcesa(Process p) {
		pasiruose_procesai.remove(p);
		sustabdyti_procesai.remove(p);
		blokuoti_procesai.remove(p);
		for (Resource r : p.resourcesInPossesion) {
			this.atlaisvintiResursa(r);
		}
		Utils.LOG("Sunaikintas procesas " + p.name);
	}

	void stabdytiProcesa(Process p) {
		pasiruose_procesai.remove(p);
		blokuoti_procesai.remove(p);
		sustabdyti_procesai.add(p);
		Utils.LOG("Sustabdytas procesas " + p.name);
	}

	void aktyvuotiProcesa(Process p) {
		sustabdyti_procesai.remove(p);
		pasiruose_procesai.add(p);
		Utils.LOG("Aktyvuotas procesas " + p.name);
	}

	// resursu primityvai
	Resource kurtiResursa(String name, String data) {
		return kurtiResursa(name, null, data);
	}

	Resource kurtiResursa(String name, Integer adresatoId, String data) {
		Resource r = new Resource(this.aktyvus_procesas, name, adresatoId, data);
		uzimti_resursai.add(r);
		this.aktyvus_procesas.generatedResources.add(r);
		Utils.LOG("Sukurtas resursas " + r.name);
		return r;
	}

	void naikintiResursa(Resource r) {
		aktyvus_procesas.resourcesInPossesion.remove(r);
		uzimti_resursai.remove(r);
		r.parent.generatedResources.remove(r);
		Utils.LOG("Sunaikintas resursas " + r.name);
	}

	void prasytiResurso(String name) {
		blokuoti_procesai.put(this.aktyvus_procesas, name);
		pasiruose_procesai.remove(this.aktyvus_procesas);
		Utils.LOG("Procesas " + this.aktyvus_procesas.name + " uzsiblokavo, nes paprase resurso " + name);
	}

	void atlaisvintiResursa(Resource r) {
		uzimti_resursai.remove(r);
		laisvi_resursai.add(r);
		aktyvus_procesas.resourcesInPossesion.remove(r);
		Utils.LOG("Procesas " + this.aktyvus_procesas.name + " atlaisvino resursa " + r.name);
	}
}
